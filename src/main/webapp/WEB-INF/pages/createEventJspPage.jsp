<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>CreateEventJspPage</title>
</head>
<body>
<form:form method="POST" modelAttribute="event" action="/events/create">
    <h3>Create category form</h3>
    <div class="form-group">
        <form:errors path="description" element="label" cssClass="alert alert-danger"/>
        <form:input path="description" class="form-control input-lg" placeholder="description"/>
    </div>
    <div class="form-group">
        <form:errors path="amount" element="label" cssClass="alert alert-danger"/>
        <form:input path="amount" class="form-control input-lg" placeholder="amount"/>
    </div>
    <div class="form-group">
        <form:select path="type">
            <form:options items="${types}"/>
        </form:select>
    </div>
    <div class="form-group">
        <form:select path="category">
            <form:options items="${categories}" itemValue="id" itemLabel="name"/>
        </form:select>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit" value="OK">Login</button>
</form:form>
</body>
</html>
