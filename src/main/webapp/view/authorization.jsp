<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <style type="text/css">
        <%@include file="../css/login.css"%>
        <%@include file="../css/modal.css"%>
    </style>
</head>
<body class="body">


<div class="containerAuth">
    <h2>Welcome!</h2>
    <form method="post" action="${pageContext.request.contextPath}/">
        <div class="inputBox">
            <input type="text" name="login" required pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>Login</span>
        </div>

        <div class="inputBox">
            <input id="password" type="password" name="password" required
                   pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>Password</span>
        </div>

        <div class="inputBox">
            <input type="text" hidden name="logIn" value="loggingIn"/>
            <input type="submit" value="Войти">
        </div>

        <div class="inputBox">
            <a href="${pageContext.request.contextPath}/catalog">Войти без авторизации</a>
        </div>

        <div class="inputBox">
            <a id="openPopUp" onclick="document.getElementById('popUp').style.display='block'">Регистрация</a>
        </div>
    </form>
</div>


<div id="popUp" class="modal">
    <span onclick="document.getElementById('popUp').style.display='none'" class="close" title="Close Modal">×</span>
    <form class="modal-content" method="post" action="${pageContext.request.contextPath}/registration">
        <div class="containerAuth">
            <div class="inputBox">
                <input type="text" name="firstName" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>First fame</span>
            </div>
            <div class="inputBox">
                <input type="text" name="lastName" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Last name</span>
            </div>
            <div class="inputBox">
                <input type="text" name="phoneNumber" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9+]{2,20}"/>
                <span>Phone number</span>
            </div>
            <div class="inputBox">
                <input type="text" name="city" required/>
                <span>City</span>
            </div>
            <div class="inputBox">
                <input type="number" name="postOffice" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9+]{2,20}"/>
                <span>Post office</span>
            </div>
            <div class="inputBox">
                <input type="text" name="login" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Login</span>
            </div>
            <div class="inputBox">
                <input type="password" name="password" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
                <span>Password</span>
            </div>
            <div class="inputBox">
                <input type="text" hidden name="loggingIn" value="loggingIn"/>
                <input type="submit" value="Registration">
            </div>
        </div>
    </form>
</div>


<script type="text/javascript" charset="utf-8">
    <%@include file="../js/modal.js"%>
    <%@include file="../js/password.js"%>
</script>
</body>
</html>
