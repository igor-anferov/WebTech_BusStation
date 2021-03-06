<%--
  Created by IntelliJ IDEA.
  User: Igor
  Date: 23.04.2017
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/styles.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/Oswald.css" />">
    <title>Изменение заказа</title>
</head>

<body>
<%@ include file="../../header.jsp" %>
<div id="heading">
    <h1>Изменение заказа</h1>
</div>
<aside>
    <nav>
        <ul class="aside-menu">
            <li><a href="/">Информация о рейсах</a></li>
            <li class="active"><a href="/clients">Информация о клиентах</a></li>
            <li class="active_submenu">Изменение заказа</li>
            <li><a href="/stations">Информация о станциях</a></li>
            <li><a href="/companies">Информация о компаниях</a></li>
        </ul>
    </nav>
</aside>
<%@ include file="../../runs/orders_add_edit_section.jsp" %>
<%@ include file="../../footer.jsp" %>
</body>

</html>

