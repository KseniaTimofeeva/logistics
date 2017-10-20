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
        <div class="row ">
            <div class="col-lg-8">
                <div class="card">
                    <div class="card-header">
                        <div class="form-group row">
                            <div class="col-sm-4 col-md-4">
                                <a href="<c:url value="/driver/order/shift-start"/>" class="btn btn-success m-2">
                                    <i class="fa fa-play-circle-o fa-lg"></i>&nbsp; <strong>Start working shift</strong>
                                </a>
                            </div>
                            <!--/.col-->
                            <div class="col-sm-8 col-md-8">
                                <a href="<c:url value="/driver/order/shift-stop"/>" class="btn btn-danger m-2">
                                    <i class="fa fa-stop-circle-o fa-lg"></i>&nbsp; <strong>Finish working shift</strong>
                                </a>
                            </div>
                            <!--/.col-->
                        </div>
                        <form action="">
                            <div class="form-group row">
                                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                <label class="col-md-3 form-control-label">Change status</label>
                                <div class="col-md-9">
                                    <div class="input-group">
                                        <select name="" class="form-control form-control-sm">
                                            <c:forEach items="<%=DriverAction.values()%>" var="action">
                                                <c:if test="${action.showActionToDriver}">
                                                    <option value="${action}" <c:if test=""> selected="selected"</c:if>>
                                                            ${action.viewName}
                                                    </option>
                                                </c:if>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary btn-sm" type="submit" role="button">Change</button>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
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
                                        <c:if test="${!pathPoint.done and pathPoint != null}">
                                            <a href="<c:url value="/driver/order/close-point/${pathPoint.id}"/>"><i class="fa fa-check-circle-o fa-lg"></i></a>
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