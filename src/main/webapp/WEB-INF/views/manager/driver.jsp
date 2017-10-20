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
    <strong>Drivers</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">

            <div class="col-lg-12">
                <div class="card">
                    <div class="card-header">
                        <a href="<c:url value="/manager/driver/new"/>" class="btn btn-primary m-1 btn-sm">
                            <i class="fa fa-plus fa-lg"></i>&nbsp; New Driver
                        </a>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered table-striped table-sm">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Personal number</th>
                                <th>First name</th>
                                <th>Last name</th>
                                <th>Current city</th>
                                <th>Status</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${allDrivers}" var="driver" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${driver.personalNumber}</td>
                                    <td>${driver.firstName}</td>
                                    <td>${driver.lastName}</td>
                                    <td>${driver.currentCity.name}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${driver.onOrder == true}">On order</c:when>
                                            <c:otherwise>Vacant</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="<c:url value="/manager/driver/new/${driver.id}"/>"><i class="fa fa-pencil fa-lg"></i></a>&nbsp;&nbsp;
                                        <a href="<c:url value="/manager/driver/delete/${driver.id}"/>"><i class="fa fa-trash-o fa-lg"></i></a>
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