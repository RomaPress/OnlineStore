<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>ADMIN</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style type="text/css">
        <%@include file="../../css/style.css"%>
    </style>
</head>
<body>

<span onclick="openNav()">
    <div class="menu">
        <b>☰</b>
    </div>
</span>
<br>

<table border="1">
    <tr>
        <th>Id</th>
        <th>Статус</th>
        <th>Время</th>
        <th>Имя Фамилия</th>
        <th>Номер тлф.</th>
        <th>Локация</th>
        <th>Накладная</th>
        <th>Товар</th>
        <th>шт</th>
        <th>Цена</th>
        <th>Сумма</th>
        <th></th>
    </tr>

    <c:forEach var="i" items="${orders}">
        <tr>

            <td><c:out value="${i.id}"/></td>
            <td><c:out value="${i.status}"/></td>
            <td><c:out value="${i.dateTime}"/></td>
            <td><c:out value="${i.user.firstName}"/> <c:out value="${i.user.lastName}"/></td>
            <td><c:out value="${i.user.phoneNumber}"/></td>
            <td>
                <c:out value="${i.city}"/> - <c:out value="${i.postOffice}"/>
            </td>
            <td><c:out value="${i.invoiceNumber}"/></td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <c:out value="${j.name}"/><br>
                </c:forEach>
            </td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <c:out value="${j.amount}"/><br>
                </c:forEach>
            </td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <c:out value="${j.price}"/><br>
                </c:forEach>
            </td>
            <td><c:out value="${i.total}"/></td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/order">
                    <button type="submit" value="${i.id}" name="order_id">Update</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
    </thead>
</table>


<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <br>
    <a href="${pageContext.request.contextPath}/order">Заказы</a>
    <a href="#">Пользователи</a>
    <a href="#">Товары</a>
    <a href="${pageContext.request.contextPath}/exit">Выйти</a>
</div>

<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/side.js"%>
</script>
</body>
</html>
