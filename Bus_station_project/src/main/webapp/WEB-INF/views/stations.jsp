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
    <title>Информация о станциях</title>
</head>

<body>
<%@ include file="header.jsp" %>
<div id="heading">
    <h1>Информация о станциях</h1>
</div>
<aside>
    <nav>
        <ul class="aside-menu">
            <li><a href="/">Информация о рейсах</a></li>
            <li><a href="/clients">Информация о клиентах</a></li>
            <li class="active">Информация о станциях</li>
            <li class="submenu"><a href="/stations/add">Добавление станции</a></li>
            <li><a href="/companies">Информация о компаниях</a></li>
        </ul>
    </nav>
</aside>
<section>
    <table class="autowidth" border="1">
        <tr>
            <th>Редактировать</th>
            <th>Станция</th>
        </tr>
        <c:forEach items="${StationsList}" var="st">
            <tr>
                <td>
                    <form name="edit_client" id="edit_client_form" action="/stations/edit" method="post">
                        <button class="edit" title="Изменить название станции" name="station" value="${st.id}" type="submit"> 📝 </button>
                    </form>
                    <form name="remove_client" id="remove_client_form" action="/stations/rm" method="post">
                        <button class="edit" title="Удалить станцию" name="station" value="${st.id}" type="submit"> ❌ </button>
                    </form>
                </td>
                <td>${st.name}</td>
            </tr>
        </c:forEach>
    </table>
</section>
<%@ include file="footer.jsp" %>
</body>

</html>