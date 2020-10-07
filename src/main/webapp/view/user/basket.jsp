<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.70/jquery.blockUI.min.js"
            type="text/javascript"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style type="text/css">
        <%@include file="../../css/modal.css"%>
    </style>
    <style type="text/css">
        <%@include file="../../css/style.css"%>
    </style>
</head>
<body>


<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <c:if test="${currentUser.role != UNKNOW}">
        <a href="${pageContext.request.contextPath}/basket">Корзина</a>
        <a href="${pageContext.request.contextPath}/profile">Мой профиль</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/exit">Выйти</a>
</div>


<!-- Use any element to open the sidenav -->
<span onclick="openNav()">
    <div class="menu">
        <b>☰</b>
    </div>
</span>


<div id="main">
    <div class="container">
        <table border="0">
            <thead>

            <tbody>
            <c:forEach var="i" items="${selectedProduct}">
                <div class="row">
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}/img/${i.id}.jpg" alt="img" width="90px"
                                 height="90px">
                        </td>
                        <td><p class="basket__text"><c:out value="${i.name}"/></p></td>
                        <td><p class="basket__text"><c:out value="${i.price}грн."/></p></td>
                        <td><p class="basket__text"><c:out value="${i.amount}шт."/></p></td>
                        <td>
                            <form method="post" action="${pageContext.request.contextPath}/basket">
                                <input type="text" hidden name="delete"/>
                                <button class=" basket__btn delete__btn" type="submit" value="${i.id}" name="deleteId">
                                    Удалить
                                </button>
                            </form>
                        </td>
                    </tr>
                </div>
                <c:set var="total" value="${total + i.price * i.amount}"/>
            </c:forEach>
            </tbody>
            </thead>
        </table>
        <br>
        Сумма = <c:out value="${total}"/>грн.
        <br>

        <form method="post" action="${pageContext.request.contextPath}/basket">
            <button class="btn" type="submit" name="order">
                Заказать
            </button>
        </form>

    </div>
</div>


<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/ajax.js"%>
    <%@include file="../../js/side.js"%>
</script>
</body>
</html>
