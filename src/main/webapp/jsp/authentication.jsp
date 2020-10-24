<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style type="text/css">
        <%@include file="../css/login.css"%>
        <%@include file="../css/modal.css"%>
        <%@include file="../css/style.css"%>
    </style>
</head>
<body class="body">


<c:if test="${empty language}">
    <c:set var="loc" value="${'prop_ru'}"/>
</c:if>
<c:if test="${!empty language}">
    <c:set var="loc" value="${language}"/>
</c:if>

<div class="containerAuth">
    <fmt:bundle basename="${loc}" prefix="authentication.">
        <h2><fmt:message key="welcome"></fmt:message></h2>
    </fmt:bundle>
    <form method="post" action="${pageContext.request.contextPath}/">
        <div class="inputBox">
            <input type="text" name="login" required pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>
                <fmt:bundle basename="${loc}" prefix="authentication.">
                    <fmt:message key="login"></fmt:message>
                </fmt:bundle>
            </span>
        </div>

        <div class="inputBox">
            <input id="password" type="password" name="password" required
                   pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"/>
            <span>
                <fmt:bundle basename="${loc}" prefix="authentication.">
                    <fmt:message key="password"></fmt:message>
                </fmt:bundle>
            </span>
        </div>

        <div class="inputBox">
            <button type="submit" name="logIn" value="loggingIn">
                <fmt:bundle basename="${loc}" prefix="authentication.">
                    <fmt:message key="log_in"></fmt:message>
                </fmt:bundle>
            </button>
        </div>

        <div class="inputBox">
            <a href="${pageContext.request.contextPath}/catalog">
                <fmt:bundle basename="${loc}" prefix="authentication.">
                    <fmt:message key="log_in_without_authentication"></fmt:message>
                </fmt:bundle>
            </a>
        </div>

        <div class="inputBox">
            <a id="openPopUp" onclick="document.getElementById('popUp').style.display='block'">
                <fmt:bundle basename="${loc}" prefix="authentication.">
                    <fmt:message key="registration"></fmt:message>
                </fmt:bundle>
            </a>
        </div>
    </form>
</div>

<div class="sidenavAut">
    <div class="loc">
        <form id="sortProduct" method="post" action="${pageContext.request.contextPath}/">
            <button class="side_btn" type="submit" name="language" value="prop_en">EN</button>
            <button class="side_btn" type="submit" name="language" value="prop_ru">RU</button>
        </form>
    </div>
</div>


<div id="popUp" class="modal">
    <c:if test="${empty language}">
        <c:set var="loc" value="${'prop_ru'}"/>
    </c:if>
    <c:if test="${!empty language}">
        <c:set var="loc" value="${language}"/>
    </c:if>
    <span onclick="document.getElementById('popUp').style.display='none'" class="close" title="Close Modal">×</span>
    <form class="modal-content" onsubmit="checkLogin()">
        <div class="containerReg">
            <div class="inputBox">
                <input type="text" id="firstName" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z]{2,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="сyrillic_latin_20_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span>
                    <fmt:bundle basename="${loc}" prefix="registration.">
                        <fmt:message key="first_name"></fmt:message>
                    </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <input type="text" id="lastName" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z]{2,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="сyrillic_latin_20_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="last_name"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <input type="text" id="phoneNumber" required
                       pattern="[0-9+() ]{5,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="numbers_20_symbol"></fmt:message>
                    </fmt:bundle> +()"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="phone_number"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <input type="text" id="city"
                       required pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z ]{2,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="сyrillic_latin_20_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="city"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <input type="number" id="postOffice"
                       required pattern="[0-9+]{,5}" min="1"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="numbers_5_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="post_office"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <input type="text" id="login" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="сyrillic_latin_numbers_20_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="login"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <input type="password" id="pass" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"
                       title="
                    <fmt:bundle basename="${loc}" prefix="title.">
                        <fmt:message key="сyrillic_latin_numbers_20_symbol"></fmt:message>
                    </fmt:bundle>"/>
                <span><fmt:bundle basename="${loc}" prefix="registration.">
                    <fmt:message key="password"></fmt:message>
                </fmt:bundle></span>
            </div>

            <div class="inputBox">
                <button type="submit" onsubmit="checkLogin()">
                    <fmt:bundle basename="${loc}" prefix="authentication.">
                        <fmt:message key="registration"></fmt:message>
                    </fmt:bundle>
                </button>
            </div>

            <input id="errorMessage" style="display: none" hidden
                   value=" <fmt:bundle basename="${loc}" prefix="error.">
                        <fmt:message key="loginExist"></fmt:message>
                    </fmt:bundle>">
        </div>
    </form>
</div>


<script type="text/javascript" charset="utf-8">
    <%@include file="../js/ajax.js"%>
    <%@include file="../js/modal.js"%>
</script>
</body>
</html>
