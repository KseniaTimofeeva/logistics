<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>

<html lang="en">
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Lukasz Holeczek">
    <!-- <link rel="shortcut icon" href="assets/ico/favicon.png"> -->

    <title>Authorization</title>

    <!-- Icons -->
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/simple-line-icons.css"/>" rel="stylesheet">

    <!-- Main styles for this application -->
    <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet">

</head>

<body class="app flex-row align-items-center">
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="clearfix">
                <h1 class="float-left display-3 mr-4">${code}</h1>
                <h4 class="pt-3">Oops! Something wrong.</h4>
                <p class="text-muted">${message}</p>
            </div>
            <div class="clearfix">
                <sec:authorize access="hasRole('ROLE_MANAGER')">
                    <a href="<c:url value="/manager/order"/>">Try to return back</a>&nbsp;
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_DRIVER')">
                    <a href="<c:url value="/driver/order"/>">Try to return back</a>&nbsp;
                </sec:authorize>
                    <a href="<c:url value="/login"/>">Return to login page</a>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap and necessary plugins -->
<script src="<c:url value="/static/js/jquery.min.js"/>"></script>
<script src="<c:url value="/static/js/popper.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>

</body>

</html>