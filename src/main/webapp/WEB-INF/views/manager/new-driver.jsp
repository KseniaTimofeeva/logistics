<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 10.10.2017
  Time: 23:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ol class="breadcrumb">
    <strong>Drivers</strong>
</ol>

<div class="container-fluid">
    <div class="animated fadeIn">
        <div class="row">
            <div class="col-sm-7">

                <div class="card">
                    <div class="card-header">
                        <c:choose>
                            <c:when test="${updatedDriver == null}">
                                <strong>New Driver</strong>
                            </c:when>
                            <c:otherwise>
                                <strong>Update Driver</strong>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <form action="<c:url value="/manager/driver/new"/>" method="post">
                        <input type="hidden" id="id" name="id" value="${updatedDriver.id}">
                        <input type="hidden" name="onOrder"
                        <c:choose>
                               <c:when test="${updatedDriver == null}">value="false"</c:when>
                               <c:otherwise>value="${updatedDriver.onOrder}"</c:otherwise>
                        </c:choose>>
                        <div class="card-body">
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label" for="first-name">First name</label>
                                <div class="col-md-9">
                                    <input type="text" id="first-name" name="firstName" class="form-control" value="${updatedDriver.firstName}"
                                           placeholder="Enter driver first name">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label" for="last-name">Last name</label>
                                <div class="col-md-9">
                                    <input type="text" id="last-name" name="lastName" class="form-control" value="${updatedDriver.lastName}"
                                           placeholder="Enter driver last name">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label" for="personal-number">Personal number</label>
                                <div class="col-md-9">
                                    <input type="text" id="personal-number" name="personalNumber" class="form-control" value="${updatedDriver.personalNumber}"
                                           placeholder="XXX123XXX123XX">
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">Current city</label>
                                <div class="col-md-9">
                                    <select name="currentCity.id" class="form-control">
                                        <c:forEach items="${cities}" var="city">
                                            <option value="${city.id}" <c:if test="${updatedDriver.currentCity.id == city.id}"> selected="selected"</c:if>>
                                                    ${city.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label" for="login">Login</label>
                                <div class="col-md-9">
                                    <input type="text" id="login" name="login" class="form-control" value="${updatedDriver.login}"
                                           placeholder="Enter driver login">
                                </div>
                            </div>
                            <c:choose>
                                <c:when test="${updatedDriver == null}">
                                    <div class="form-group row">
                                        <label class="col-md-3 form-control-label" for="new-password">Password</label>
                                        <div class="col-md-9">
                                            <input type="text" id="new-password" name="password" class="form-control"
                                                   placeholder="Enter password for the first entry">
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <input type="hidden" id="old-password" name="password" value="${updatedDriver.password}">
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="card-footer">
                            <c:choose>
                                <c:when test="${updatedDriver == null}">
                                    <button type="submit" class="btn btn-success" role="button">
                                        <i class="fa fa-check-circle-o fa-lg"></i> Add
                                    </button>
                                    <button type="reset" class="btn btn-danger">
                                        <i class="fa fa-times-circle-o fa-lg"></i> Reset
                                    </button>
                                    <input type="hidden" name="role" value="ROLE_DRIVER"/>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-success" role="button">
                                        <i class="fa fa-check-circle-o fa-lg"></i> Update
                                    </button>
                                </c:otherwise>
                            </c:choose>
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
