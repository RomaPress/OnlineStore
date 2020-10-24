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
        <div class="col">
            <div id="main">

                <table border="0">
                    <thead>
                    <th>id</th>
                    <th><p>Имя Фамилия</p></th>
                    <th><p>Роль</p></th>
                    <th></th>
                    </thead>
                    <tbody>
                    <c:forEach var="j" items="${users}">
                        <tr>
                            <td><c:out value="${j.id}"/></td>
                            <td><c:out value="${j.firstName}"/> <c:out value="${j.lastName}"/></td>
                            <td><c:out value="${j.role}"/></td>
                            <c:if test="${j.role == 'BLOCK'}">
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/controlUser">
                                        <input type="text" hidden value="unlock" name="action"/>
                                        <button type="submit" value="${j.id}" name="user_id">
                                            Разблокировать
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                            <c:if test="${j.role != 'BLOCK'}">
                                <td>
                                    <form method="post" action="${pageContext.request.contextPath}/controlUser">
                                        <input type="text" hidden value="block" name="action"/>
                                        <button type="submit" value="${j.id}" name="user_id">
                                            Блокировать
                                        </button>
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/side.js" %>
</script>
</body>
</html>
