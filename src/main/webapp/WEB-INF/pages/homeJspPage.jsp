<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>HomeJsp</title>
</head>
<body>
<a href="<c:url value="/events/create"/>">Create new Event</a>
<a href="<c:url value="/categories/create"/>">Create new Category</a>
<table>
    <c:forEach items="${categories}" var="category">
        <c:set var="createAt" value="${category.createAt}"/>
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td>${category.description}</td>
            <td><fmt:formatDate value="${createAt}" pattern="yyyy-MM-dd"/></td>
            <td>${category.capacity}</td>
            <td><a href="/categories/edit/${category.id}">Edit</a></td>
            <td><a href="/categories/remove/${category.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br>
<table>
    <c:forEach items="${eventHistory}" var="event">
        <tr>
            <td>${event.id}"</td>
            <td>${event.description}</td>
            <td><fmt:formatDate value="${event.createAt}" pattern="yyyy-MM-dd" /></td>
            <td>${event.amount}</td>
            <td>${event.type}</td>
            <td>${event.category.name}</td>
            <td><a href="/events/edit/${event.id}">Edit</a></td>
            <td><a href="/events/remove/${event.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
