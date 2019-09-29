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
    <c:when test="${category.id==null}">
        <c:set var="urlAction" value="/categories/create"/>
    </c:when>
    <c:otherwise>
        <c:set var="urlAction" value="/categories/edit"/>
    </c:otherwise>
</c:choose>
<form:form method="POST" modelAttribute="category" action="${urlAction}">
    <h3>Registration form</h3>
    <form:input type="hidden" path="id"/>
    <div class="form-group">
        <form:errors path="name" element="label" cssClass="alert alert-danger"/>
        <form:input path="name" class="form-control input-lg" placeholder="Name"/>
    </div>
    <div class="form-group">
        <form:errors path="capacity" element="label" cssClass="alert alert-danger"/>
        <form:input path="capacity" class="form-control input-lg" placeholder="Capacity"/>
    </div>
    <div class="form-group">
        <form:errors path="description" element="label" cssClass="alert alert-danger"/>
        <form:input path="description" class="form-control input-lg" placeholder="Description"/>
    </div>
    <div class="form-group">
        <form:errors path="createAt" element="label" cssClass="alert alert-danger"/>
        <form:input path="createAt" class="form-control input-lg" type="Date"/>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit" value="OK">Login</button>
</form:form>
<br>
<c:if test="${eventHistory!=null}">
    <table>
        <c:forEach items="${eventHistory}" var="event">
            <tr>
                <td>${event.id}"</td>
                <td>${event.description}</td>
                <td><fmt:formatDate value="${event.createAt}" pattern="yyyy-MM-dd"/></td>
                <td>${event.amount}</td>
                <td>${event.type}</td>
                <td>${event.category.name}</td>
                <td><a href="/events/edit/${event.id}">Edit</a></td>
                <td><a href="/events/remove/${event.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
</body>
</html>
