<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style type="text/css">
        <%@include file="../../css/test.css"%>
    </style>
</head>
<body>


<div class="container">
    <h2>Авторизируйтесь!</h2>
    <form method="post" action="${pageContext.request.contextPath}/">
        <div class="inputBox">
            <input type="text" name="login" required pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>Login</span>
        </div>

        <div class="inputBox">
            <input id="password"  type="password" name="password" required pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>Password</span>
        </div>

        <div class="inputBox">
                <input type="text" hidden name="loggingIn" value="loggingIn"/>
                <input  type="submit" value="Ввойти">
        </div>

        <div class="inputBox">
            <a href="${pageContext.request.contextPath}/catalog">Ввойти без авторизации</a>
        </div>

        <div class="inputBox">
            <a href="${pageContext.request.contextPath}/registration">Регистрация</a>
        </div>
    </form>
</div>
















<%--<form method="get"  action="${pageContext.request.contextPath}/registration">--%>
<%--    <input  type="submit" value="Регистрация">--%>
<%--</form>--%>

<%--<br><br><br><br><br>--%>
<%--<form method="post" action="${pageContext.request.contextPath}/">--%>
<%--    --%>
<%--    <br><br>--%>
<%--    <input id="password"  type="password" name="password" required placeholder="password" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>--%>
<%--    <br>--%>
<%--    <input type="checkbox" onclick="myFunction()">Show Password--%>
<%--    <br><br>--%>
<%--    <input type="text" hidden name="loggingIn" value="loggingIn"/>--%>
<%--    <input  type="submit" value="Ввойти">--%>
<%--</form>--%>

<%--<form method="get" action="${pageContext.request.contextPath}/catalog">--%>
<%--    <input type="text" hidden name="notLoggingIn" value="notLoggingIn"/>--%>
<%--    <input  type="submit" value="Ввойти без авторизации">--%>
<%--</form>--%>


<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/password.js"%>
</script>
</body>
</html>
