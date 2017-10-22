<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 13.10.2017
  Time: 23:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- Breadcrumb -->
<ol class="breadcrumb">
    <strong>Orders</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-sm-6 col-md-6">
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            <div class="col-md-3">
                                <strong>Truck info:</strong>
                            </div>
                            <div class="col-md-9">
                                <c:if test="${orderInfo.crew.truck != null}">
                                    <span class="js-current-truck-id">
                                        №:&nbsp;${orderInfo.crew.truck.numberPlate} - ${orderInfo.crew.truck.capacity}tns</span>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col-lg-12">
                                <c:if test="${orderInfo.crew.truck != null and suitableTrucks.isCurrentTruckSuitable == false}">
                                    <div class="alert alert-danger">
                                        Chosen truck capacity is less than the maximum weight of the cargo. Please choose another truck.
                                    </div>
                                </c:if>
                                <form action="<c:url value="/manager/order/${orderInfo.id}/choose-truck"/>" method="post">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="input-group">
                                        <select name="truckId" class="form-control form-control-sm">
                                            <option disabled="disabled" selected="selected">Choose truck...</option>
                                            <c:forEach items="${suitableTrucks.trucks}" var="suitableTruck">
                                                <option value="${suitableTruck.id}">
                                                        ${suitableTruck.numberPlate} - ${suitableTruck.workingShift}hrs - ${suitableTruck.capacity}tns
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary btn-sm" type="submit" role="button">Choose</button>
                                        </span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.col-->
            <div class="col-sm-6 col-md-6">
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            <div class="col-md-3">
                                <strong>Drivers info:</strong>
                            </div>
                            <div class="col-md-9">
                                <c:if test="${orderInfo.crew.users != null}">
                                    <table>
                                        <tbody>
                                        <c:forEach items="${orderInfo.crew.users}" var="driver">
                                            <c:forEach items="${suitableDrivers.notSuitableDrivers}" var="nsDriver">
                                                <c:if test="${driver.id == nsDriver.id}">
                                                    <c:set var="marked" value="true"/>
                                                </c:if>
                                            </c:forEach>
                                            <tr>
                                                <td><span <c:if test="${marked}">style="color: red"</c:if>>${driver.personalNumber}:&nbsp;</span></td>
                                                <td>${driver.firstName} ${driver.lastName}&nbsp;</td>
                                                <td><a href="<c:url value="/manager/order/${orderInfo.id}/detach-driver/${driver.id}"/>">
                                                    <i class="fa fa-times"></i></a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col-lg-12">
                                <c:if test="${orderInfo.crew.users != null and !suitableDrivers.notSuitableDrivers.isEmpty()}">
                                    <div class="alert alert-danger">
                                        Drivers are not suitable. Please choose another drivers.
                                    </div>
                                </c:if>
                                <form action="<c:url value="/manager/order/${orderInfo.id}/choose-driver"/>" method="post">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <div class="input-group">
                                        <select name="driverId" class="form-control form-control-sm js-driver-before-truck">
                                            <option disabled="disabled" selected="selected">Choose driver...</option>
                                            <c:forEach items="${suitableDrivers.drivers}" var="suitableDriver">
                                                <option value="${suitableDriver.id}">
                                                        ${suitableDriver.personalNumber}: ${suitableDriver.firstName} ${suitableDriver.lastName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary btn-sm js-driver-before-truck" type="submit" role="button">Add</button>
                                        </span>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.col-->
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            <div class="col-lg-3">
                                <a href="<c:url value="/manager/order/${orderInfo.id}/new-point"/>" class="btn btn-primary m-1 btn-sm">
                                    <i class="fa fa-plus fa-lg"></i>&nbsp; New Point&nbsp;&nbsp;
                                </a>
                            </div>
                            <div class="col-lg-6">
                                <c:if test="${hasCargoToUnload}">
                                    <div class="alert alert-danger">
                                        There is unloaded cargo. Please create way point to unloading.
                                    </div>
                                </c:if>
                            </div>
                            <div class="col-lg-3 text-right">
                                №:&nbsp; <strong>${orderInfo.number}</strong>&nbsp;(${orderInfo.status.viewName})
                            </div>
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

                            <c:forEach items="${orderInfo.pathPoints}" var="pathPoint" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${pathPoint.cargo.number}</td>
                                    <td>${pathPoint.cargo.name}</td>
                                    <td>${pathPoint.cargo.weight}</td>
                                    <td>${pathPoint.city.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pathPoint.loading}">loading</c:when>
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
                                        <a href="<c:url value="/manager/order/${orderInfo.id}/new-point/${pathPoint.id}"/>"><i
                                                class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;
                                        <a href="<c:url value="/manager/order/${orderInfo.id}/delete/${pathPoint.id}"/>"><i class="fa fa-trash-o fa-lg"></i></a>
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