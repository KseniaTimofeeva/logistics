<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 10.10.2017
  Time: 16:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ol class="breadcrumb">
    <strong>Orders</strong>
</ol>


<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-lg-12">
                <div class="card">
                    <div class="card-header">
                        <a href="<c:url value="/manager/order/new"/>" class="btn btn-primary m-1 btn-sm">
                            <i class="fa fa-plus fa-lg"></i>&nbsp; New Order
                        </a>
                    </div>
                    <div class="card-body">
                        <table class="table table-bordered table-striped table-sm">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th>Number</th>
                                <th>Path points qty</th>
                                <th>Truck number plate</th>
                                <th>Drivers</th>
                                <th>Finished</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${allOrders}" var="order" varStatus="i">
                                <tr>
                                    <td>${i.count}</td>
                                    <td>${order.number}</td>
                                    <td>${order.pathPoints.size()}</td>
                                    <td>${order.crew.truck.numberPlate}</td>
                                    <td>
                                        <c:forEach items="${order.crew.users}" var="user" varStatus="i">
                                            ${user.firstName}&nbsp;${user.lastName}&nbsp;${user.personalNumber}
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:if test="${order.finished == true}">yes</c:if>
                                        <c:if test="${order.finished == false}">no</c:if>
                                    </td>
                                    <td>
                                        <a href="<c:url value="/manager/order/${order.id}"/>"><i class="fa fa-info-circle fa-lg"></i></a>
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