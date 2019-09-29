<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%--@elvariable id="user" type="by.sivko.cashsaving.dto.RegistrationUserDto"--%>
<div class="container login-container">
    <form:form method="POST" modelAttribute="user" action="/registration">
        <h3>Registration form</h3>
        <div class="form-group">
            <form:errors path="username" element="label" cssClass="alert alert-danger"/>
            <form:input path="username" class="form-control input-lg" placeholder="Username"/>
        </div>
        <div class="form-group">
            <form:errors path="email" element="label" cssClass="alert alert-danger"/>
            <form:input path="email" class="form-control input-lg"/>
        </div>
        <div class="form-group">
            <form:errors path="password" element="label" cssClass="alert alert-danger"/>
            <form:input path="password" class="form-control input-lg" type="password"/>
        </div>
        <div class="form-group">
            <form:errors path="confirmPassword" element="label" cssClass="alert alert-danger"/>
            <form:input path="confirmPassword" class="form-control input-lg" type="password"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="OK">Login</button>
    </form:form>
</div>
</body>
</html>