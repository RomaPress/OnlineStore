<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>


</head>
<body>
<form method="get"  action="${pageContext.request.contextPath}/registration">
    <input  type="submit" value="Регистрация">
</form>
<br><br><br><br><br>
<form method="post" action="${pageContext.request.contextPath}/authorization">
    <input  type="text" name="login" required placeholder="login" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>
    <input  type="password" name="password" required placeholder="password" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>
    <input type="text" hidden name="loggingIn" value="loggingIn"/>
    <input  type="submit" value="Ввойти">
</form>

<form method="get" action="${pageContext.request.contextPath}/catalog">
    <input type="text" hidden name="notLoggingIn" value="notLoggingIn"/>
    <input  type="submit" value="Ввойти без авторизации">
</form>






</body>
</html>
