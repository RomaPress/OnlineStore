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


<h1>Catalog products</h1>




<form method="get" action="${pageContext.request.contextPath}/basket">
    <input type="submit" value="Корзина" >
</form>
<br>
<%-------------------------------ТОВАР-------------------------------%>
<table border="1" >
    <thead>
    <tr>
        <th scope="col"><p>Id</p></th>
        <th scope="col"><p>Name</p></th>
        <th scope="col"><p>Цена</p></th>
        <th scope="col"><p>Amount</p></th>
        <th scope="col"><p>Description</p></th>
        <th scope="col"><p></p></th>
    </tr>
    <tbody>
    <c:forEach var="i" items="${products}">
        <tr>

            <td><p><c:out value="${i.id}"/></p></td>
            <td><p><c:out value="${i.name}"/></p></td>
            <td><p><c:out value="${i.price}"/></p></td>
            <td><p><c:out value="${i.amount}"/></p></td>
            <td><p><c:out value="${i.description}"/></p></td>
            <td>
                <input type="number" id="amount<c:out value="${i.id}"/>" min="1" max="${i.amount}"/>
                <button type="button" id="btn" value="${i.id}" onclick=addProduct(this)>Add</button>
            </td>

        </tr>
    </c:forEach>
    </tbody>
    </thead>
</table>
<%-------------------------------ТОВАР-------------------------------%>

<%-------------------------------POPUP-------------------------------%>


<%-------------------------------POPUP-------------------------------%>



<script type="text/javascript" charset="utf-8">
    <%@include file="../js/popUp.js"%>
</script>




</body>
</html>
