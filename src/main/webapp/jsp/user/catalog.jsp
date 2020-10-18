<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">


    <style type="text/css">
        <%@include file="../../css/style.css"%>
        <%@include file="../../css/login.css"%>
    </style>
    <script type="text/javascript">
        <%--var products = ${products};--%>
    </script>
</head>
<body>
<c:if test="${empty language}">
    <c:set var="loc" value="${'prop_ru'}"/>
</c:if>
<c:if test="${!empty language}">
    <c:set var="loc" value="${language}"/>
</c:if>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>

    <c:if test="${currentUser.role != UNKNOW}">
        <a href="${pageContext.request.contextPath}/basket">Корзина</a>
        <a href="${pageContext.request.contextPath}/profile">Мой профиль</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/exit">Выйти</a>
</div>
<div class="container-fluid">
    <div class="row">
        <div class="col-1">
            <!-- Use any element to open the sidenav -->
            <span onclick="openNav()">
                 <div class="menu">
                      <b>☰</b>
                </div>
            </span>
        </div>

        <div class="col-11">
            <div id="main">
                <h1>
                    <fmt:bundle basename="${loc}" prefix="catalog.">
                        <fmt:message key="catalog_products"></fmt:message>
                    </fmt:bundle>
                </h1>
                <div class="container my__container">
                    <div class="row">
                        <%--                        <c:forEach var="i" items="${products}">--%>
                        <%--                            <div class="col-3 my__col product">--%>
                        <%--                                <div class="catalog__img">--%>
                        <%--                                    <img src="${pageContext.request.contextPath}/img/${i.id}.jpg" alt="img"--%>
                        <%--                                         width="175px" height="175px">--%>
                        <%--                                </div>--%>
                        <%--                                <p class="name__product"><b><c:out value="${i.name}"/></b><br>--%>
                        <%--                                    <c:out value="${i.price}"/>грн.--%>
                        <%--                                </p>--%>
                        <%--                                <p><c:out value="${i.amount}"/> на складе</p>--%>

                        <%--                                <div class="form_input_number">--%>
                        <%--                                    <button class="my__catalogBtn minus" type="button"--%>
                        <%--                                            onclick="this.nextElementSibling.stepDown()">--%>
                        <%--                                        <b>-</b></button>--%>
                        <%--                                    <input class="my__inputN" type="number" readonly id="amount<c:out value="${i.id}"/>"--%>
                        <%--                                           min="1"--%>
                        <%--                                           max="${i.amount}"/>--%>
                        <%--                                    <button class="my__catalogBtn plus" type="button"--%>
                        <%--                                            onclick="this.previousElementSibling.stepUp()"><b>+</b></button>--%>
                        <%--                                    <br>--%>
                        <%--                                    <button class="my__catalogBtn btn" type="button" value="${i.id}"--%>
                        <%--                                            onclick=addProduct(this)>--%>
                        <%--                                        В корзину--%>
                        <%--                                    </button>--%>
                        <%--                                </div>--%>
                        <%--                            </div>--%>
                        <%--                        </c:forEach>--%>
                        <div id="demo"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript" src="https://pagination.js.org/dist/2.1.5/pagination.min.js"></script>
<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/ajax.js"%>
    <%@include file="../../js/side.js"%>
</script>
</body>

</html>
