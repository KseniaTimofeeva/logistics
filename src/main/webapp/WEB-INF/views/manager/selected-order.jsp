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
                            <strong>Truck info:</strong>&nbsp;&nbsp;${orderInfo.crew.truckDto.numberPlate}&nbsp;&nbsp;${orderInfo.crew.truckDto.capacity}
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col-lg-12">
                                <form action="<c:url value="/manager/order/${orderInfo.id}/choose-truck"/>" method="post">
                                    <div class="input-group">
                                        <select name="select" class="form-control form-control-sm">
                                            <option value="0">Please select</option>
                                            <option value="1">Option #1</option>
                                            <option value="2">Option #2</option>
                                            <option value="3">Option #3</option>
                                        </select>
                                        <span class="input-group-btn">
                                            <button class="btn btn-secondary btn-sm" type="button">Choose</button>
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
                    <div class="card-body">
                        <div class="row">
                            <strong>Drivers info:</strong>
                        </div>
                    </div>
                    <div class="row">
                        <table class="table table-sm">
                            <tbody>
                            <c:forEach items="${orderInfo.crew.driverDtoList}" var="driver" varStatus="i">
                                <tr>
                                    <td>${driver.firstName}&nbsp;${driver.lastName}</td>
                                    <td>${driver.personalNumber}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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
                            <div class="col-lg-6">
                                <a href="<c:url value="/manager/order/${orderInfo.id}/new-point"/>" class="btn btn-primary m-1 btn-sm">
                                    <i class="fa fa-plus fa-lg"></i>&nbsp; New Point&nbsp;&nbsp;
                                </a>
                                <a href="<c:url value="/manager/order/${orderInfo.id}/choose-driver"/>" class="btn btn-primary m-1 btn-sm">
                                    <i class="fa fa-plus fa-lg"></i>&nbsp; Add Driver
                                </a>
                            </div>
                            <div class="col-lg-6 text-right">
                                №:&nbsp; <strong>${orderInfo.number}</strong>&nbsp;
                                <c:choose>
                                    <c:when test="${orderInfo.finished == true}">(finished)</c:when>
                                    <c:otherwise>(in process)</c:otherwise>
                                </c:choose>
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
                                            <c:when test="${pathPoint.loading == true}">loading</c:when>
                                            <c:otherwise>unloading</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${pathPoint.cargo.status}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${pathPoint.done == true}">yes</c:when>
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