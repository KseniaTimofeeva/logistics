<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div class="card-group mb-0">
                <div class="card p-4">
                    <div class="card-body">
                        <h1>Login</h1>
                        <p class="text-muted">Sign In to your account</p>
                        <form action="<c:url value="/login"/>" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <div class="input-group mb-3">
                                <span class="input-group-addon"><i class="icon-user"></i>
                                </span>
                                <input type="text" class="form-control" placeholder="Username" name="login">
                            </div>
                            <div class="input-group mb-4">
                                <span class="input-group-addon"><i class="icon-lock"></i>
                                </span>
                                <input type="password" class="form-control" placeholder="Password" name="password">
                            </div>
                            <div class="row">
                                <div class="col-6">
                                    <button type="submit" role="button" class="btn btn-primary px-4" >Login</button>
                                </div>
                                <div class="col-6 text-right">
                                    <button type="button" class="btn btn-link px-0">Forgot password?</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
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