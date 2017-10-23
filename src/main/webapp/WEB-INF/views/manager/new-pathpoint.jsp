<%@ page import="com.tsystems.app.logistics.entity.enums.CargoStatus" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ksenia
  Date: 14.10.2017
  Time: 14:45
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
                                <c:choose>
                                    <c:when test="${updatedPoint == null}">
                                        <strong>New Point</strong>
                                    </c:when>
                                    <c:otherwise>
                                        <strong>Update Point</strong>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="col-lg-6 text-right">
                                №:&nbsp; <strong>${orderInfo.number}</strong>&nbsp;(${orderInfo.status.viewName})
                            </div>
                        </div>
                    </div>

                    <form action="<c:url value="/manager/order/${orderInfo.id}/new-point"/>" method="post">
                        <input type="hidden" name="orderId" value="${orderInfo.id}">
                        <input type="hidden" name="id" value="${updatedPoint.id}">
                        <input type="hidden" name="cargo.id" value="${updatedPoint.cargo.id}" <c:if test="${updatedPoint.id==null}">disabled="disabled"</c:if>>
                        <div class="card-body">
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">Type</label>
                                <div class="col-md-9">
                                    <label class="radio-inline">
                                        <input type="radio" name="loading" value="true" id="js-radio-to-click-loading"
                                        <c:if test="${updatedPoint.loading or updatedPoint == null}"> checked="checked"</c:if>>
                                        Loading&nbsp;&nbsp;&nbsp;
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="loading" value="false" id="js-radio-to-click-unloading"
                                               <c:if test="${!updatedPoint.loading and updatedPoint != null}">checked="checked"</c:if>>Unloading
                                    </label>
                                </div>
                            </div>
                            <div class="form-group row js-load-cargo-div">
                                <label class="col-md-3 form-control-label">Cargo number</label>
                                <div class="col-md-9">
                                    <input type="text" name="cargo.number" class="form-control js-load-cargo-input" value="${updatedPoint.cargo.number}"
                                           placeholder="Enter cargo number">
                                </div>
                            </div>
                            <div class="form-group row js-load-cargo-div">
                                <label class="col-md-3 form-control-label">Description</label>
                                <div class="col-md-9">
                                    <input type="text" name="cargo.name" class="form-control js-load-cargo-input" value="${updatedPoint.cargo.name}"
                                           placeholder="Cargo name">
                                </div>
                            </div>
                            <div class="form-group row js-load-cargo-div">
                                <label class="col-md-3 form-control-label">Weight (kgs)</label>
                                <div class="col-md-9">
                                    <input type="text" name="cargo.weight" class="form-control js-load-cargo-input" value="${updatedPoint.cargo.weight}"
                                           placeholder="1000.00">
                                </div>
                            </div>

                            <div class="form-group row js-unload-cargo-div">
                                <label class="col-md-3 form-control-label">Cargo info</label>
                                <div class="col-md-9">
                                    <select name="cargo.id" class="form-control js-unload-cargo-input">
                                        <c:forEach items="${pointsWithCargoToUnload}" var="point">
                                            <option value="${point.cargo.id}">
                                                    №:&nbsp;${point.cargo.number}&nbsp;-&nbsp;${point.cargo.weight}kgs</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">City</label>
                                <div class="col-md-9">
                                    <select name="city.id" class="form-control">
                                        <c:forEach items="${cities}" var="city">
                                            <option value="${city.id}" <c:if test="${updatedPoint.city.id == city.id}"> selected="selected"</c:if>>
                                                    ${city.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row js-load-cargo-div">
                                <label class="col-md-3 form-control-label">Cargo status</label>
                                <div class="col-md-9">
                                    <select name="cargo.status" class="form-control js-load-cargo-input">
                                        <c:forEach items="<%=CargoStatus.values()%>" var="status" varStatus="i">
                                            <option value="${status}" <c:if test="${updatedPoint.cargo.status == status}"> selected="selected"</c:if>>
                                                ${status.viewName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-md-3 form-control-label">Finished</label>
                                <div class="col-md-9">
                                    <label class="radio-inline">
                                        <input type="radio" disabled="disabled"
                                                <c:if test="${updatedPoint.done == true}"> checked="checked"</c:if>/>
                                        Yes&nbsp;&nbsp;&nbsp;
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" disabled="disabled"
                                               <c:if test="${updatedPoint.done == false or updatedPoint == null}">checked="checked"</c:if>/>No
                                    </label>
                                    <input type="hidden" name="done"
                                            <c:choose>
                                                <c:when test="${updatedPoint == null}">value="false"</c:when>
                                                <c:otherwise>value="${updatedPoint.done}"</c:otherwise>
                                            </c:choose>
                                    />
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                            <c:choose>
                                <c:when test="${updatedPoint == null}">
                                    <button type="submit" class="btn btn-success" role="button">
                                        <i class="fa fa-check-circle-o fa-lg"></i> Add
                                    </button>
                                    <button type="reset" class="btn btn-danger">
                                        <i class="fa fa-times-circle-o fa-lg"></i> Reset
                                    </button>
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
</div>