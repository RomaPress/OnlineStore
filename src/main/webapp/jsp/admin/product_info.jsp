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
<div class="container-fluid">
    <div class="row">
        <div class="col-1">
<span onclick="openNav()">
    <div class="menu">
        <b>☰</b>
    </div>
</span>
        </div>
        <div id="main">
            <div class="col">
                <h1><c:out value="${product.name}"/></h1>
                <hr>
                <div class="row">
                    <div class="col-6">
                        <img src="${pageContext.request.contextPath}/img/${product.id}.jpg" alt="img"
                             width="175px" height="175px">
                    </div>
                    <div class="col">
                        <form method="post" action="${pageContext.request.contextPath}/changeProduct">
                            <div class="text_row">
                                <strong>Id</strong> <c:out value="${product.id}"/><br>
                            </div>
                            <div class="text_row">
                                <strong>Тип</strong> <c:out value="${product.type.name}"/><br>
                            </div>
                            <div class="text_row">
                                <strong>Цена</strong>
                                <input type="number" name="price" required value="${product.price}"
                                       pattern="[.0-9+]{,3}" min="1" step="0.01"/>
                                <br>
                            </div>
                            <div class="text_row">
                                <strong>Количество</strong>
                                <input type="number" name="amount" required value="${product.amount}"
                                       pattern="[0-9+]{,4}" min="0" step="1"/>
                                <br>
                            </div>
                            <div class="text_row">
                                <strong>Описание</strong> <c:out value="${product.description}"/><br>
                            </div>
                            <button type="submit" value="${product.id}" name="product_id">Изменить</button>
                            <input type="text" hidden name="change"/>

                        </form>
                        <form method="post" action="${pageContext.request.contextPath}/changeProduct">
                            <button type="submit" value="${product.id}" name="product_id">Удалить</button>
                            <input type="text" hidden name="delete"/>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
            <br>
            <a href="${pageContext.request.contextPath}/order">Заказы</a>
            <a href="${pageContext.request.contextPath}/controlUser">Пользователи</a>
            <a href="${pageContext.request.contextPath}/controlProduct">Товары</a>
            <a href="${pageContext.request.contextPath}/exit">Выйти</a>
        </div>
    </div>
</div>

<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/side.js" %>
</script>

</body>
</html>
