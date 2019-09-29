<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:choose>
    <c:when test="${event.id==null}">
        <c:set var="urlAction" value="/events/create"/>
    </c:when>
    <c:otherwise>
        <c:set var="urlAction" value="/events/edit"/>
    </c:otherwise>
</c:choose>
<form:form method="POST" modelAttribute="event" action="${urlAction}">
    <h3>Registration form</h3>
    <form:input type="hidden" path="id"/>
    <div class="form-group">
        <form:errors path="description" element="label" cssClass="alert alert-danger"/>
        <form:input path="description" class="form-control input-lg" placeholder="Description"/>
    </div>
    <div class="form-group">
        <form:errors path="amount" element="label" cssClass="alert alert-danger"/>
        <form:input path="amount" class="form-control input-lg" placeholder="Amount"/>
    </div>
    <div class="form-group">
        <form:errors path="createAt" element="label" cssClass="alert alert-danger"/>
        <form:input path="createAt" class="form-control input-lg" type="Date"/>
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
