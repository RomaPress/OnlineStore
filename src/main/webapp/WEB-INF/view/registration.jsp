<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/registration">
    <input  type="text" name="firstName" required placeholder="firstName" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>
    <input  type="text" name="lastName" required placeholder="lastName" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>
    <input  type="text" name="phoneNumber" required placeholder="+380" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9+]{2,20}"/>
    <br><br>
    <input  type="text" name="login" required placeholder="login" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>
    <input  type="password" name="password" required placeholder="password" pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
    <br><br>


    <input type="text" hidden name="loggingIn" value="loggingIn"/>
    <input  type="submit" value="GO!">




</form>
</body>
</html>
