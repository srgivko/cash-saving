<%--@elvariable id="_csrf" type="org.springframework.security.web.csrf.CsrfToken"--%>
<%--@elvariable id="errorMessge" type="java.lang.String"--%>
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
<div class="container login-container">
    <h1>Login</h1>
    <h1>Spring Security 5 - Login Form</h1>


    <c:if test="${not empty errorMessage}">
        <div style="color:red; font-weight: bold; margin: 30px 0px;">${errorMessage}</div>
    </c:if>

    <form name='login' action="/login" method='POST'>
        <input type='text' name='username' value=''>
        <input type='password' name='password'/>
        <input name="submit" type="submit" value="submit"/>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
</body>
</html>
