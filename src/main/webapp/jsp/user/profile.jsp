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
        <%@include file="../../css/modal.css"%>
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
            <div>
                <a id="openPopUp" onclick="document.getElementById('popUp').style.display='block'">
                    <fmt:bundle basename="${loc}" prefix="profile.">
                        <fmt:message key="change_profile_information."></fmt:message>
                    </fmt:bundle>
                </a>
            </div>
            <div class="col-11">
                <h2>
                    <fmt:bundle basename="${loc}" prefix="profile.">
                        <fmt:message key="my_orders"></fmt:message>
                    </fmt:bundle>
                </h2>
                <p></p>

                <table>
                    <tr>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="status"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="city"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="post_office"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="invoice_number"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="total"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="product"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="unit_price"></fmt:message>
                            </fmt:bundle>
                        </th>
                        <th>
                            <fmt:bundle basename="${loc}" prefix="profile.">
                                <fmt:message key="amount"></fmt:message>
                            </fmt:bundle>
                        </th>
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
    <c:if test="${empty language}">
        <c:set var="loc" value="${'prop_ru'}"/>
    </c:if>
    <c:if test="${!empty language}">
        <c:set var="loc" value="${language}"/>
    </c:if>
    <form class="modal-content" method="get" action="${pageContext.request.contextPath}/profile" onsubmit="checkPassword()">
        <div class="containerAuth">
            <div class="inputBox">
                <input type="text" id="firstName" required value="${currentUser.firstName}"
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
                <input type="text" id="lastName" required value="${currentUser.lastName}"
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z]{2,20}"
                       title="
                       <fmt:bundle basename="${loc}" prefix="title.">
                            <fmt:message key="сyrillic_latin_20_symbol"></fmt:message>
                       </fmt:bundle>"/>
                <span>
                     <fmt:bundle basename="${loc}" prefix="registration.">
                         <fmt:message key="last_name"></fmt:message>
                     </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <input type="text" id="phoneNumber" required value="${currentUser.phoneNumber}"
                       pattern="[0-9+()]{5,20}"
                       title="
                       <fmt:bundle basename="${loc}" prefix="title.">
                            <fmt:message key="numbers_20_symbol"></fmt:message>
                       </fmt:bundle>"+()/>
                <span>
                     <fmt:bundle basename="${loc}" prefix="registration.">
                         <fmt:message key="phone_number"></fmt:message>
                     </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <input type="text" id="city" required value="${currentUser.city}"
                       pattern="[\sА-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z+]{2,20}"
                       title="
                       <fmt:bundle basename="${loc}" prefix="title.">
                            <fmt:message key="сyrillic_latin_20_symbol"></fmt:message>
                       </fmt:bundle>"/>
                <span>
                     <fmt:bundle basename="${loc}" prefix="registration.">
                         <fmt:message key="city"></fmt:message>
                     </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <input type="number" id="postOffice" required value="${currentUser.postOffice}"
                       pattern="[0-9]{,5}" min="1"
                       title="
                       <fmt:bundle basename="${loc}" prefix="title.">
                            <fmt:message key="numbers_5_symbol"></fmt:message>
                       </fmt:bundle>"/>
                <span>
                     <fmt:bundle basename="${loc}" prefix="registration.">
                         <fmt:message key="post_office"></fmt:message>
                     </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <input type="password" id="password" required
                       pattern="[А-Яа-яЁёІіїЇєЄэЭыЫъЪA-Za-z0-9]{2,20}"
                       title="
                       <fmt:bundle basename="${loc}" prefix="title.">
                            <fmt:message key="сyrillic_latin_numbers_20_symbol"></fmt:message>
                       </fmt:bundle>"/>
                <span>
                     <fmt:bundle basename="${loc}" prefix="profile.">
                         <fmt:message key="current_password"></fmt:message>
                     </fmt:bundle>
                </span>
            </div>

            <div class="inputBox">
                <button type="submit" >
                    <fmt:bundle basename="${loc}" prefix="profile.">
                        <fmt:message key="change"></fmt:message>
                    </fmt:bundle>
                </button>
            </div>
            <input id="errorMessage" style="display: none" hidden
                   value=" <fmt:bundle basename="${loc}" prefix="error.">
                        <fmt:message key="incorrect_password"></fmt:message>
                    </fmt:bundle>">
        </div>
    </form>
</div>


<div id="mySidenav" class="sidenav">
    <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">×</a>
    <a href="${pageContext.request.contextPath}/catalog">
        <fmt:bundle basename="${loc}" prefix="side.">
            <fmt:message key="catalog"></fmt:message>
        </fmt:bundle>
    </a>

    <c:if test="${currentUser.role != UNKNOW}">
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
        <form id="sortProduct" method="post" action="${pageContext.request.contextPath}/profile">
            <button class="side_btn" type="submit" name="language" value="prop_en">EN</button>
            <button class="side_btn" type="submit" name="language" value="prop_ru">RU</button>
        </form>
    </div>
</div>

<script type="text/javascript" charset="utf-8">
    <%@include file="../../js/ajax.js"%>
    <%@include file="../../js/side.js"%>
    <%@include file="../../js/modal.js"%>
</script>

</body>
</html>
