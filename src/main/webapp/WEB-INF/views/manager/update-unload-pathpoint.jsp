<%@ page import="com.tsystems.app.logistics.entity.enums.CargoStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 29.11.2017
  Time: 20:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<ol class="breadcrumb">
    <strong>Orders</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-sm-7">
                <c:if test='<%= request.getParameter("error") != null%>'>
                    <div class="alert alert-danger">
                        <span style="color: red">Form error</span>
                    </div>
                </c:if>
                <div class="card">
                    <div class="card-header">
                        <div class="row">
                            <div class="col-lg-6">
                                <strong>Update Point</strong>
                            </div>
                            <div class="col-lg-6 text-right">
                                №:&nbsp; <strong>${orderInfo.number}</strong>&nbsp;(${orderInfo.status.viewName})
                            </div>
                        </div>
                    </div>

                    <form action="<c:url value="/manager/order/${orderInfo.id}/new-point"/>" method="post">
                        <input type="hidden" name="orderId" value="${orderInfo.id}">
                        <input type="hidden" name="id" value="${updatedPoint.id}">
                        <input type="hidden" name="cargo.id" value="${updatedPoint.cargo.id}">
                        <input type="hidden" name="cargo.status" value="${updatedPoint.cargo.status}">
                        <input type="hidden" name="done" value="${updatedPoint.done}">
                        <input type="hidden" name="loading" value="${updatedPoint.loading}">
                        <input type="hidden" name="cargo.number" value="${updatedPoint.cargo.number}">
                        <input type="hidden" name="cargo.name" value="${updatedPoint.cargo.name}">
                        <input type="hidden" name="cargo.weight" value="${updatedPoint.cargo.weight}">


                        <div class="card-body">
                            <div class="form-group row">
                                №:&nbsp;${updatedPoint.cargo.number}&nbsp; ${updatedPoint.cargo.name} - &nbsp;${updatedPoint.cargo.weight}kgs
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">City</label>
                                <div class="col-md-9">
                                    <select name="city.id" class="form-control form-control-sm">
                                        <c:forEach items="${cities}" var="city">
                                            <option value="${city.id}" <c:if
                                                    test="${updatedPoint.city.id == city.id}"> selected="selected"</c:if>>
                                                    ${city.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-success" role="button">
                                <i class="fa fa-check-circle-o fa-lg"></i> Update
                            </button>
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </div>
                    </form>
                </div>
            </div>
            <!--/.col-->
        </div>
        <!--/.row-->
    </div>
</div>
<!-- /.conainer-fluid -->