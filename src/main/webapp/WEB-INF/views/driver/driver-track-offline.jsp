<%@ page import="com.tsystems.app.logistics.entity.enums.DriverAction" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 25.11.2017
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ol class="breadcrumb">
    <strong>Time track offline</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-sm-9">
                <form action="<c:url value="/driver/track-offline/add-action"/>" method="post" class="form-horizontal">

                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="form-group col-sm-3">
                                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                                    <input type="hidden" name="order.id" value="${currentOrder.id}"/>
                                    <div class="input-group">
                                        <select name="driverAction" class="form-control form-control-sm">
                                            <c:forEach items="<%=DriverAction.values()%>" var="action">
                                                <option value="${action}">${action.viewName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group col-sm-4">
                                    <div class="input-group" id="datetimepickerFrom">
                                        <input type="text" class="form-control form-control-sm"/>
                                        <span class="input-group-addon">
                                            <span class="fa fa-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                                <div class="form-group col-sm-4">
                                    <div class="input-group" id="datetimepickerTo">
                                        <input type="text" class="form-control form-control-sm"/>
                                        <span class="input-group-addon">
                                            <span class="fa fa-calendar"></span>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $(function () {
            $('#datetimepickerFrom').datetimepicker();
        });
        $(function () {
            $('#datetimepickerTo').datetimepicker();
        });
    });
</script>
