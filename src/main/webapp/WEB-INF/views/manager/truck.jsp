<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 10.10.2017
  Time: 18:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Breadcrumb -->
<ol class="breadcrumb">
    <strong>Trucks</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-header">
                        <a href="<c:url value="/manager/truck/new"/>" class="btn btn-primary m-1 btn-sm">
                            <i class="fa fa-plus fa-lg"></i>&nbsp; New Truck
                        </a>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered table-striped table-sm">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Number plate</th>
                                <th>Working shift</th>
                                <th>Capacity</th>
                                <th>Functioning</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${allTrucks}" var="truck" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${truck.numberPlate}</td>
                                    <td>${truck.workingShift}</td>
                                    <td>${truck.capacity}</td>
                                    <c:if test="${truck.functioning == true}">
                                        <td>yes</td>
                                    </c:if>
                                    <c:if test="${truck.functioning == false}">
                                        <td>no</td>
                                    </c:if>
                                    <td>
                                        <a href="<c:url value="/manager/truck/new/${truck.id}"/>"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;
                                        <a href="<c:url value="/manager/truck/delete/${truck.id}"/>"><i class="fa fa-trash-o fa-lg"></i></a>
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