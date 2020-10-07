<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style type="text/css">
        <%@include file="../../css/style.css"%>
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-1">
<span onclick="openNav()">
    <div class="menu">
        <b>☰</b>
    </div>
</span>
        </div>
        <div class="col">
            <div id="main">
                <p>№ <c:out value="${order.id}"/></p>
                <p>Статус <c:out value="${order.status}"/></p>
                <p>Дата регистрации <c:out value="${order.dateTime}"/></p>
                <p>Заказчик <c:out value="${order.user.firstName}"/> <c:out value="${order.user.lastName}"/></p>
                <p>Номер телефона <c:out value="${order.user.phoneNumber}"/></p>
                <p>Город <c:out value="${order.city}"/></p>
                <p>Отделение НП <c:out value="${order.postOffice}"/></p>
                <p>Стоимость заказа <c:out value="${order.total}"/></p>

                <br>
                <table border="0">
                    <thead>
                    <tr>
                        <th><p>Товар</p></th>
                        <th><p>шт</p></th>
                        <th><p>Цена шт.</p></th>
                    </tr>
                    <tbody>
                    <c:forEach var="j" items="${order.products}">
                        <tr>
                            <td><c:out value="${j.name}"/></td>
                            <td><c:out value="${j.amount}"/></td>
                            <td><c:out value="${j.price}"/> грн.</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/changeOrder">
                                    <input type="text" hidden name="delete"/>
                                    <button type="submit" value="${j.id}" name="product_id">Удалить</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                    </thead>
                </table>
            </div>
        </div>
    </div>
</div>
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
