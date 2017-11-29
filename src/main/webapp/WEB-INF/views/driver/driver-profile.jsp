<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 18.10.2017
  Time: 0:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ol class="breadcrumb">
    <strong>Driver profile</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-sm-9 col-md-9">
                <div class="card">
                    <div class="card-header">
                        Personal number:&nbsp;&nbsp;<strong>${driverProfile.personalNumber}</strong>
                    </div>
                    <div class="card-body">
                        <div class="form-group row">
                            <div class="col-md-3 col-sm-5">
                                Order number:
                            </div>
                            <div class="col-md-9 col-sm-7">
                                <c:if test="${driverProfile.crew != null}">${driverProfile.crew.order.number}</c:if>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-3 col-sm-5">
                                Truck number plate:
                            </div>
                            <div class="col-md-9 col-sm-7">
                                <c:if test="${driverProfile.crew != null}">${driverProfile.crew.truck.numberPlate}</c:if>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-md-3 col-sm-5">
                                Co-drivers info:
                            </div>
                            <div class="col-md-9 col-sm-7">
                                <c:if test="${driverProfile.crew.users != null and !driverProfile.crew.users.isEmpty()}">
                                    <table class="table table-sm">
                                        <thead>
                                        <tr>
                                            <th>Personal number</th>
                                            <th>First name</th>
                                            <th>Last name</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${driverProfile.crew.users}" var="driver">
                                            <tr>
                                                <td>${driver.personalNumber}</td>
                                                <td>${driver.firstName}</td>
                                                <td>${driver.lastName}</td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--/.col-->-
        </div>
    </div>
</div>