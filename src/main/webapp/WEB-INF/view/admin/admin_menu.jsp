<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>ADMIN</title>
</head>
<body>
<h1>Hello ADMIN!</h1>

<table border="1">
    <thead>
    <tr>
        <th><p>Id</p></th>
        <th><p>Статус</p></th>
        <th><p>Время</p></th>
        <th><p>Имя Фамилия</p></th>
        <th><p>Номер тлф.</p></th>
        <th>Товар</th>
        <th>шт</th>
        <th>Цена шт.</th>
        <th><p>Сумма</p></th>
        <th></th>

    </tr>
    <tbody>
    <c:forEach var="i" items="${orders}">
        <tr>

            <td><p><c:out value="${i.id}"/></p></td>
            <td><p><c:out value="${i.status}"/></p></td>
            <td><p><c:out value="${i.dateTime}"/></p></td>
            <td><p><c:out value="${i.user.firstName}"/> <c:out value="${i.user.lastName}"/></p></td>
            <td><p><c:out value="${i.user.phoneNumber}"/></p></td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <table>
                        <td><c:out value="${j.name}"/></td>

                    </table>
                </c:forEach>
            </td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <table>
                        <td><c:out value="${j.amount}"/></td>
                    </table>
                </c:forEach>
            </td>
            <td>
                <c:forEach var="j" items="${i.products}">
                    <table>
                        <td><c:out value="${j.price}"/> грн.</td>
                    </table>
                </c:forEach>
            </td>
            <td><c:out value="${i.total}"/></td>
            <td>
                <form method="get" action="${pageContext.request.contextPath}/changeOrder">
                    <input type="text" hidden name="update"/>
                    <button type="submit" value="${i.id}" name="id">Update</button>
                </form>

<%--                <form method="get"action="${pageContext.request.contextPath}/catalog">--%>
<%--                    <input type="number" hidden name="order_id" value="${i.id}" />--%>
<%--                    <input type="submit"  name="update" value="update"/>--%>
<%--                </form>--%>

            </td>
        </tr>
    </c:forEach>
    </tbody>
    </thead>
</table>
</body>
</html>
