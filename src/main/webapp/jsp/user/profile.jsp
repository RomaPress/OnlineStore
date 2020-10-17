<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <%@include file="../../css/modal.css"%>
        <%@include file="../../css/login.css"%>
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
            <div>
                <a id="openPopUp" onclick="document.getElementById('popUp').style.display='block'">
                    Изменить данные профиля
                </a>
            </div>
            <div class="col-11">
                <h2>Мои заказы</h2>
                <p></p>

                <table>
                    <tr>
                        <th>Статус</th>
                        <th>Город</th>
                        <th>Отделение НП</th>
                        <th>Номер накладной</th>
                        <th>Сумма</th>
                        <th>Товар</th>
                        <th>Цена за шт.</th>
                        <th>Количество</th>
                    </tr>
                    <c:forEach var="i" items="${userOrder}">
                        <tr>
                            <td>${i.status}</td>
                            <td>${i.city}</td>
                            <td>${i.postOffice}</td>
                            <td>${i.invoiceNumber}</td>
                            <td>${i.total}</td>
                            <td>
                                <c:forEach var="j" items="${i.products}">
                                    <c:out value="${j.name}"/><br>
                                </c:forEach>
                            <td>
                                <c:forEach var="j" items="${i.products}">
                                    <c:out value="${j.price}"/>грн.<br>
                                </c:forEach>
                            </td>
                            <td>
                                <c:forEach var="j" items="${i.products}">
                                    <c:out value="${j.amount}"/>шт.<br>
                                </c:forEach>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</div>

<div id="popUp" class="modal">
            <span onclick="dropPopUp()" class="close"
                  title="Close Modal">×</span>
    <form class="modal-content" method="post" action="${pageContext.request.contextPath}/profile">
        <div class="containerAuth">
            <div class="inputBox">
                <input type="text" id="firstName" required value="${currentUser.firstName}"
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>First fame</span>
            </div>
            <div class="inputBox">
                <input type="text" id="lastName" required value="${currentUser.lastName}"
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Last name</span>
            </div>
            <div class="inputBox">
                <input type="text" id="phoneNumber" required value="${currentUser.phoneNumber}"
                       pattern="[0-9+]{2,20}"/>
                <span>Phone number</span>
            </div>
            <div class="inputBox">
                <input type="text" id="city" required value="${currentUser.city}"
                       pattern="[\sА-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9+]{2,20}"/>
                <span>City</span>
            </div>
            <div class="inputBox">
                <input type="number" id="postOffice" required value="${currentUser.postOffice}"
                       pattern="[0-9+]{,3}" min="1"  />
                <span>Post office</span>
            </div>
            <div class="inputBox">
                <input type="password" id="password" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Current password</span>
            </div>
            <div class="inputBox">
                <input  type="button" value="Изменить" onclick="checkPassword()">
            </div>

            <div id="passwordError" style="display: none">
                <p>Password is incorrect!</p>
            </div>
        </div>
    </form>
</div>


<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="${pageContext.request.contextPath}/catalog">Каталог</a>
    <c:if test="${currentUser.role != UNKNOW}">
        <a href="${pageContext.request.contextPath}/basket">Корзина</a>
        <a href="${pageContext.request.contextPath}/profile">Мой профиль</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/exit">Выйти</a>
</div>

<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/ajax.js"%>
    <%@include file="../../js/side.js"%>
    <%@include file="../../js/modal.js"%>
    <%@include file="../../js/password.js"%>
</script>

</body>
</html>
