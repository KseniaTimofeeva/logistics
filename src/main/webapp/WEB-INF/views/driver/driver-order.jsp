<%@ page import="com.tsystems.app.logistics.entity.enums.DriverAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 18.10.2017
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Breadcrumb -->
<ol class="breadcrumb">
    <strong>Current order</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <c:if test="${currentOrder != null}">
            <div class="row ">
                <div class="col-sm-8">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="form-group col-lg-4">
                                    <div class="input-group">
                                        <form action="<c:url value="/driver/order/add-action"/>" method="post" class="form-horizontal">
                                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            <input type="hidden" name="driverAction" value="START_WORKING_SHIFT"/>
                                            <input type="hidden" name="order.id" value="${currentOrder.id}"/>
                                            <button type="submit" class="btn btn-success btn-sm"
                                                    <c:set var="endWorkingShift" value="<%=DriverAction.END_WORKING_SHIFT%>"/>
                                                    <c:if test="${lastAction.driverAction != null and lastAction.driverAction != endWorkingShift}"> disabled="disabled"</c:if>>
                                                <i class="fa fa-play-circle-o fa-lg"></i>&nbsp; <strong>Start</strong>
                                            </button>
                                        </form>
                                        <form action="<c:url value="/driver/order/add-action"/>" method="post" class="form-horizontal">
                                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                            <input type="hidden" name="lastAction" value="${lastAction.driverAction}"/>
                                            <input type="hidden" name="driverAction" value="END_WORKING_SHIFT"/>
                                            <input type="hidden" name="order.id" value="${currentOrder.id}"/>
                                            <button type="submit" class="btn btn-danger btn-sm"
                                                    <c:if test="${lastAction.driverAction == null or lastAction.driverAction == endWorkingShift}"> disabled="disabled"</c:if>>
                                                <i class="fa fa-stop-circle-o fa-lg"></i>&nbsp; <strong>Finish</strong>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                                <div class="form-group col-lg-8">
                                    <form action="<c:url value="/driver/order/add-action"/>" method="post" class="form-horizontal">
                                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                        <input type="hidden" name="order.id" value="${currentOrder.id}"/>
                                        <input type="hidden" name="lastAction" value="${lastAction.driverAction}"/>
                                        <div class="input-group">
                                            <select name="driverAction" class="form-control form-control-sm"
                                                    <c:if test="${lastAction.driverAction == null or lastAction.driverAction == endWorkingShift}"> disabled="disabled"</c:if>>
                                                <c:forEach items="<%=DriverAction.values()%>" var="action">
                                                    <c:if test="${action.showActionToDriver}">
                                                        <option value="${action}" <c:if
                                                                test="${lastAction.driverAction == action}"> selected="selected"</c:if>>
                                                                ${action.viewName}
                                                        </option>
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                            <span class="input-group-btn">
                                                        <button class="btn btn-secondary btn-sm" type="submit" role="button"
                                                                <c:if test="${lastAction.driverAction == null or lastAction.driverAction == endWorkingShift}"> disabled="disabled"
                                                                </c:if>>Change status
                                                        </button>
                                                    </span>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            Order №:&nbsp;&nbsp;<c:if test="${currentOrder != null}"><strong>${currentOrder.number}</strong></c:if>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered table-striped table-sm">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Cargo number</th>
                                <th>Cargo name</th>
                                <th>Weight</th>
                                <th>City</th>
                                <th>Type</th>
                                <th>Status</th>
                                <th>Finished</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="firstDisabledPoint" value="-1"/>
                            <c:forEach items="${currentOrder.pathPoints}" var="pathPoint" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${pathPoint.cargo.number}</td>
                                    <td>${pathPoint.cargo.name}</td>
                                    <td>${pathPoint.cargo.weight}</td>
                                    <td>${pathPoint.city.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pathPoint.loading == true}">loading</c:when>
                                            <c:otherwise>unloading</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${pathPoint.cargo.status.viewName}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pathPoint.done}">yes</c:when>
                                            <c:otherwise>no</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${lastAction.driverAction != null and lastAction.driverAction != endWorkingShift}">
                                            <c:if test="${pathPoint != null and !pathPoint.done}">
                                                <a href="<c:url value="/driver/order/close-point/${pathPoint.id}"/>" <c:if
                                                        test="${firstDisabledPoint == i.count}"> class="cs-a-disabled" onclick="return false;" </c:if>><i
                                                        class="fa fa-check-circle-o fa-lg"></i></a>
                                                <c:set var="firstDisabledPoint" value="${i.count+1}"/>
                                            </c:if>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!--/.col-->
        </div>
        <!--/.row-->
    </div>
</div>
<!-- /.conainer-fluid -->