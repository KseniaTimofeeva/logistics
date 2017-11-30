<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="author" content="Łukasz Holeczek">
    <title>Logistics</title>

    <!-- Icons -->
    <link href="<c:url value="/static/css/font-awesome.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/simple-line-icons.css"/>" rel="stylesheet">

    <!-- Main styles for this application -->
    <link href="<c:url value="/static/css/style.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/custom-style.css"/>" rel="stylesheet">
    <link href="<c:url value="/static/css/bootstrap-datetimepicker.min.css"/>" rel="stylesheet">
</head>

<body class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
<header class="app-header navbar">
    <button class="navbar-toggler mobile-sidebar-toggler d-lg-none mr-auto" type="button">☰</button>
    <a class="navbar-brand" href="#"></a>

    <ul class="nav navbar-nav ml-auto mr-lg-4">
        <li class="nav-item">
            <span>
                <sec:authorize access="hasRole('ROLE_DRIVER')">&nbsp; Driver</sec:authorize>
                <sec:authorize access="hasRole('ROLE_MANAGER')">&nbsp; Manager</sec:authorize>
            </span>
        </li>
        <li class="nav-item">
            <form id="logout-form" action="<c:url value="/logout"/>" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            </form>
            <a class="nav-link" href="#" onclick="document.getElementById('logout-form').submit(); return false;">
                <i class="fa fa-sign-out fa-lg"></i>
            </a>
        </li>
    </ul>
</header>

<div class="app-body">
    <div class="sidebar">
        <nav class="sidebar-nav">
            <sec:authorize access="hasRole('ROLE_DRIVER')">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/driver/order"/>"><i class="fa fa-file-text-o fa-lg"></i> Current order</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/driver/profile"/>"><i class="icon-user"></i> Profile</a>
                    </li>
                    <%--<li class="nav-item">--%>
                        <%--<a class="nav-link" href="<c:url value="/driver/track-offline"/>"><i class="fa fa-clock-o fa-lg"></i> Time track offline</a>--%>
                    <%--</li>--%>
                </ul>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_MANAGER')">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/manager/order"/>"><i class="icon-layers icons"></i> Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/manager/driver"/>"><i class="icon-people icons"></i> Drivers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/manager/truck"/>"><i class="fa fa-truck fa-lg"></i> Trucks</a>
                    </li>
                </ul>
            </sec:authorize>
        </nav>
        <button class="sidebar-minimizer brand-minimizer" type="button"></button>
    </div>

    <script src="<c:url value="/static/js/jquery.min.js"/>"></script>
    <script>
        var basePath = '<c:url value="/"/>';
        var csrf = '${_csrf.token}';
    </script>

    <!-- Main content -->
    <main class="main">
        <jsp:include page="${typeOfCenter}"/>
    </main>

</div>

<footer class="app-footer">
    © 2017
</footer>

<!-- Bootstrap and necessary plugins -->
<script src="<c:url value="/static/js/popper.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/static/js/moment.min.js"/>"></script>
<script src="<c:url value="/static/js/bootstrap-datetimepicker.min.js"/>"></script>

<!-- GenesisUI main scripts -->
<script src="<c:url value="/static/js/app.js"/>"></script>

<script src="<c:url value="/static/js/script.js"/>"></script>

</body>
</html>
