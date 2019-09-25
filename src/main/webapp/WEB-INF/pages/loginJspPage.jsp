<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="<spring:url value="resources/css/bootstrap.min.cs"/>"/>
    <link rel="stylesheet" href="<spring:url value="resources/css/main.css"/>"/>
    <script src="<spring:url value="resources/js/jquery-3.4.1.min.js"/>"></script>
    <script src="<spring:url value="resources/js/bootstrap.min.js"/>"></script>
    <script src="<spring:url value="resources/js/main.js"/>"></script>
</head>
<body>
<div class="container login-container">
    <div class="row">
        <form class="col-md-6 login-form-1" action="<spring:url value="login"/>" method="post">
            <%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
            <h3>Login Form 1</h3>
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Your Email or Username *" value="" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="Your Password *" value="" name="password">
            </div>
            <div class="form-group">
                <input type="submit" class="btn btn-form btn-primary" value="Register">
            </div>
            <div class="form-group">
                <a href="#" class="btnForgetPwd">Forget Password?</a>
            </div>
            <label>
                <input hidden name="${_csrf.parameterName}" value="${_csrf.token}">
            </label>
        </form>
        <img class="logo-img" src="https://image.ibb.co/n7oTvU/logo_white.png" alt="">
    </div>
</div>
</body>
</html>
