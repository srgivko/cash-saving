<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Create category</title>
</head>
<body>
<div class="container login-container">
    <form:form method="POST" modelAttribute="category" action="/categories/create">
        <h3>Create category form</h3>
        <div class="form-group">
            <form:errors path="name" element="label" cssClass="alert alert-danger"/>
            <form:input path="name" class="form-control input-lg" placeholder="Username"/>
        </div>
        <div class="form-group">
            <form:errors path="description" element="label" cssClass="alert alert-danger"/>
            <form:input path="description" class="form-control input-lg" placeholder="Username"/>
        </div>
        <div class="form-group">
            <form:errors path="capacity" element="label" cssClass="alert alert-danger"/>
            <form:input path="capacity" class="form-control input-lg" placeholder="Username"/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" value="OK">Login</button>
    </form:form>
</div>
</body>
</html>
