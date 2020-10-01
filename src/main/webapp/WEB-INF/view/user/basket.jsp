<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.70/jquery.blockUI.min.js"
            type="text/javascript"></script>
</head>
<body>
<table border="1">
    <thead>
    <tr>
        <th scope="col"><p>Id</p></th>
        <th scope="col"><p>Name</p></th>
        <th scope="col"><p>Цена</p></th>
        <th scope="col"><p>Amount</p></th>
        <th scope="col"><p></p></th>
    </tr>
    <tbody>
    <c:forEach var="i" items="${selectedProduct}">
        <tr>

            <td><p><c:out value="${i.id}"/></p></td>
            <td><p><c:out value="${i.name}"/></p></td>
            <td><p><c:out value="${i.price}"/></p></td>
            <td><p><c:out value="${i.amount}"/></p></td>

            <td>
                <form method="post" action="${pageContext.request.contextPath}/basket">
                    <input type="text" hidden name="delete"/>
                    <button type="submit" value="${i.id}" name="deleteId">Delete</button>
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
    </thead>
</table>

<br>
<form method="post" action="${pageContext.request.contextPath}/catalog">
    <button type="submit" name="order">Delete</button>
</form>


<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/popUp.js"%>
</script>
</body>
</html>
