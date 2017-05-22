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
    <title>Информация о компаниях</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div id="heading">
    <h1>Информация о компаниях</h1>
</div>
<aside>
    <nav>
        <ul class="aside-menu">
            <li><a href="/">Информация о рейсах</a></li>
            <li><a href="/clients">Информация о клиентах</a></li>
            <li><a href="/stations">Информация о станциях</a></li>
            <li class="active">Информация о компаниях</li>
            <li class="submenu"><a href="/companies/add">Добавление компании</a></li>
        </ul>
    </nav>
</aside>
<section>
    <table class="autowidth" border="1">
        <tr>
            <th>Редактировать</th>
            <th>Компания</th>
        </tr>
        <c:forEach items="${CompaniesList}" var="company">
            <tr>
                <td>
                    <form name="edit_client" id="edit_client_form" action="/companies/edit" method="post">
                        <button class="edit" title="Изменить название компании" name="company" value="${company.id}" type="submit"> 📝 </button>
                    </form>
                    <form name="remove_client" id="remove_client_form" action="/companies/rm" method="post">
                        <button class="edit" title="Удалить компанию" name="company" value="${company.id}" type="submit"> ❌ </button>
                    </form>
                </td>
                <td>${company.name}</td>
            </tr>
        </c:forEach>
    </table>
</section>
<%@ include file="footer.jsp" %>
</body>

</html>