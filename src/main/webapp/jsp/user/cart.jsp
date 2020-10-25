<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Online Shop</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.blockUI/2.70/jquery.blockUI.min.js"
            type="text/javascript"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
    <style type="text/css">
        <%@include file="../../css/modal.css"%>
        <%@include file="../../css/style.css"%>
        <%@include file="../../css/login.css"%>
    </style>
</head>
<body>
<c:if test="${empty language}">
    <c:set var="loc" value="${'prop_ru'}"/>
</c:if>
<c:if test="${!empty language}">
    <c:set var="loc" value="${language}"/>
</c:if>


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
            <div class="col-11">
                <c:if test="${empty selectedProduct}">
                    <h1>
                        <fmt:bundle basename="${loc}" prefix="cart.">
                            <fmt:message key="cart_is_empty"></fmt:message>
                        </fmt:bundle>
                    </h1>
                    <hr>
                    <h3>
                        <fmt:bundle basename="${loc}" prefix="cart.">
                            <fmt:message key="message"></fmt:message>
                        </fmt:bundle>
                    </h3>
                </c:if>
                <c:if test="${!empty selectedProduct}">
                    <h1>
                        <fmt:bundle basename="${loc}" prefix="cart.">
                            <fmt:message key="cart"></fmt:message>
                        </fmt:bundle>
                    </h1>
                    <table border="0">
                        <thead>
                        <tbody>
                        <c:forEach var="i" items="${selectedProduct}">
                            <div class="row">
                                <tr>
                                    <td>
                                        <img src="${pageContext.request.contextPath}/img/${i.value.id}.jpg" alt="img"
                                             width="90px"
                                             height="90px">
                                    </td>
                                    <td><p class="basket__text"><c:out value="${i.value.name}"/></p></td>
                                    <td><p class="basket__text"><c:out value="${i.value.price}грн."/></p></td>
                                    <td><p class="basket__text"><c:out value="${i.value.amount}"/>
                                    </p></td>
                                    <td>
                                        <form method="post" action="${pageContext.request.contextPath}/cart">
                                            <input type="text" hidden name="delete"/>
                                            <button class=" basket__btn delete__btn" type="submit" value="${i.value.id}"
                                                    name="deleteId">
                                                <fmt:bundle basename="${loc}" prefix="cart.">
                                                    <fmt:message key="delete"></fmt:message>
                                                </fmt:bundle>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </div>
                            <c:set var="total" value="${total + i.value.price * i.value.amount}"/>
                        </c:forEach>
                        </tbody>
                        </thead>
                    </table>
                    <br>
                    <fmt:bundle basename="${loc}" prefix="cart.">
                        <fmt:message key="total"></fmt:message>
                    </fmt:bundle> = <c:out value="${total}"/>₴
                    <br>
                    <form method="post" action="${pageContext.request.contextPath}/cart">
                        <button class="btn" type="submit" name="order">
                            <fmt:bundle basename="${loc}" prefix="cart.">
                                <fmt:message key="order"></fmt:message>
                            </fmt:bundle>
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>


<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="${pageContext.request.contextPath}/catalog">
        <fmt:bundle basename="${loc}" prefix="side.">
            <fmt:message key="catalog"></fmt:message>
        </fmt:bundle>
    </a>

    <c:if test="${currentUser.role != UNKNOW || currentUser.role != BLOCK}">
        <a href="${pageContext.request.contextPath}/cart">
            <fmt:bundle basename="${loc}" prefix="side.">
                <fmt:message key="cart"></fmt:message>
            </fmt:bundle>
        </a>
        <a href="${pageContext.request.contextPath}/profile">
            <fmt:bundle basename="${loc}" prefix="side.">
                <fmt:message key="my_profile"></fmt:message>
            </fmt:bundle>
        </a>
    </c:if>
    <a href="${pageContext.request.contextPath}/exit">
        <fmt:bundle basename="${loc}" prefix="side.">
            <fmt:message key="log_out"></fmt:message>
        </fmt:bundle>
    </a>
    <div class="loc">
        <form id="sortProduct" method="post" action="${pageContext.request.contextPath}/cart">
            <button class="side_btn" type="submit" name="language" value="prop_en">EN</button>
            <button class="side_btn" type="submit" name="language" value="prop_ru">RU</button>
        </form>
    </div>
</div>

<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/ajax.js"%>
    <%@include file="../../js/side.js"%>
</script>
</body>
</html>
