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
        <%@include file="../../css/style.css"%>
    </style>
<%--    <script type="text/javascript">--%>
<%--        var quantity = 0;--%>
<%--        var products = ${products};--%>
<%--    </script>--%>
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
        <div class="row">
            <h1>Catalog products</h1>
        </div>
    </div>
    <div class="container my__container">
        <div class="row">
            <c:forEach var="i" items="${products}">
                <div class="col-3 my__col product">
                    <div class="catalog__img">
                        <img src="${pageContext.request.contextPath}/img/${i.id}.jpg" alt="img" width="175px"
                             height="175px">
                    </div>
                    <p class="name__product"><b><c:out value="${i.name}"/></b><br> <c:out value="${i.price}"/>грн.</p>
                    <p><c:out value="${i.amount}"/> на складе</p>

                    <div class="form_input_number">
                        <button class="my__catalogBtn minus" type="button" onclick="this.nextElementSibling.stepDown()">
                            <b>-</b></button>
                        <input class="my__inputN" type="number" readonly id="amount<c:out value="${i.id}"/>" min="1"
                               max="${i.amount}"/>
                        <button class="my__catalogBtn plus" type="button"
                                onclick="this.previousElementSibling.stepUp()"><b>+</b></button>
                        <br>
                        <button class="my__catalogBtn btn" type="button" value="${i.id}" onclick=addProduct(this)>
                            В корзину
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>


    <script type="text/javascript" charset="utf-8">
        <%@include file="../../js/ajax.js"%>
        <%@include file="../../js/side.js"%>
    </script>
</body>

</html>
