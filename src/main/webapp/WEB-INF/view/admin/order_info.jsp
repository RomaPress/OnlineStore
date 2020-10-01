<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Info</h1>


<p>№ <c:out value="${order.id}"/></p>
<p>Статус <c:out value="${order.status}"/></p>
<p>Дата регистрации <c:out value="${order.dateTime}"/></p>
<p>Заказчик <c:out value="${order.user.firstName}"/> <c:out value="${order.user.lastName}"/></p>
<p>Номер телефона <c:out value="${order.user.phoneNumber}"/></p>
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
        </tr>
    </c:forEach>
    </tbody>
    </thead>
</table>


</body>
</html>
