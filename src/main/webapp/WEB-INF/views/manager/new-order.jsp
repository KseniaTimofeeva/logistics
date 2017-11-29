<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 13.10.2017
  Time: 16:24
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
                <c:if test='<%=request.getParameter("error") != null%>'>
                    <c:set var="code" value="<%=request.getParameter(\"error\")%>"/>
                    <div class="alert alert-danger">
                        <span style="color: red"><spring:message code="${code}"/> </span>
                    </div>
                </c:if>
                <div class="card">
                    <div class="card-header">
                        <strong>New Order</strong>
                    </div>
                    <form action="<c:url value="/manager/order/new"/>" method="post">
                        <div class="card-body">
                            <div class="form-group">
                                <label for="number">Enter order number</label>
                                <input type="text" class="form-control" id="number" name="number">
                            </div>
                        </div>
                        <div class="card-footer">
                            <button type="submit" class="btn btn-success" role="button">
                                <i class="fa fa-check-circle-o fa-lg"></i> Create
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

