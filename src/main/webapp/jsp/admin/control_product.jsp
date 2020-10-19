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
        <div class="col">
            <div id="main">
                <h3><a id="openPopUp" onclick="document.getElementById('popUp').style.display='block'">Добавить продукт</a></h3>
                <br>
                <table border="0">
                    <thead>
                    <th>id</th>
                    <th>Товар</th>
                    <th>Тип</th>
                    <th>Цена</th>
                    <th>Количество</th>
                    <th></th>
                    </thead>
                    <tbody>
                    <c:forEach var="i" items="${products}">
                        <tr>
                            <td><c:out value="${i.id}"/></td>
                            <td><c:out value="${i.name}"/></td>
                            <td><c:out value="${i.type.name}"/></td>
                            <td><c:out value="${i.price}"/></td>
                            <td><c:out value="${i.amount}"/></td>
                            <td>
                                <form method="get" action="${pageContext.request.contextPath}/changeProduct">
                                    <button type="submit" value="${i.id}" name="product_id">Изменить</button>
                                    <input type="text" hidden value="true" name="update"/>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div id="popUp" class="modal">
    <span onclick="dropPopUp()" title="Close Modal">×</span>
    <form class="modal-content" method="post" action="${pageContext.request.contextPath}/controlProduct" enctype="multipart/form-data">
        <div class="containerAuth">
            <div class="inputBox">
                <input type="text" name="name" required
                       pattern="[\s\nА-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Название</span>
            </div>
            <div class="inputBox">
                <select id="types" name="type">
                    <c:forEach var="i" items="${types}">
                        <option><c:out value="${i}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="inputBox">
                <input type="number" name="price" required min="1" step="0.01"
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{1,7}"/>
                <span>Цена</span>
            </div>
            <div class="inputBox">
                <input type="number" name="amount" required min="1" step="1"
                       pattern="[0-9]{1,5}"/>
                <span>Количество</span>
            </div>
            <div class="inputBox">
                <input type="text" name="description"
                       pattern="[ А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9+]+"/>
                <span>Описание</span>
            </div>

            <input type="file" name="file" required/>
            <br><br>

            <div class="inputBox">
                <input type="submit" name="createProduct" value="Registration">
            </div>
        </div>
    </form>
</div>

<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <br>
    <a href="${pageContext.request.contextPath}/order">Заказы</a>
    <a href="${pageContext.request.contextPath}/controlUser">Пользователи</a>
    <a href="${pageContext.request.contextPath}/controlProduct">Товары</a>
    <a href="${pageContext.request.contextPath}/exit">Выйти</a>
</div>
<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/side.js" %>
    <%@include file="../../js/modal.js"%>
    <%@include file="../../js/password.js"%>
</script>
</body>
</html>
