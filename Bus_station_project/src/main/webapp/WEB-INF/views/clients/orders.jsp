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
    <title>Информация о заказах</title>
</head>

<body>
<div id="wrapper">

    <%@ include file="../header.jsp" %>

    <div id="heading">
        <h1>Информация о заказах</h1>
    </div>
    <aside>
        <nav>
            <ul class="aside-menu">
                <li><a href="/">Информация о рейсах</a></li>
                <li class="active"><a href="/clients">Информация о клиентах</a></li>
                <li class="active_submenu">Информация о заказах</li>
            </ul>
        </nav>
    </aside>
    <section>
        <h2>Заказы клиента ${Client.lastName} ${Client.firstName} ${Client.patronymic}</h2>
        <table border="1">
            <tr>
                <th>Редактировать</th>
                <th>Компания</th>
                <th>Номер рейса</th>
                <th>Пункт отправления</th>
                <th>Время отправления</th>
                <th>Пункт прибытия</th>
                <th>Время прибытия</th>
                <th>Цена</th>
                <th>Количество билетов</th>
                <th>Сумма заказа</th>
            </tr>
            <c:forEach items="${Client.orders}" var="order">
                <tr>
                    <td>
                        <button class="edit" title="Редактировать данные" type="submit"> 📝 </button>
                        <button class="edit" title="Удалить информацию о клиенте" type="submit"> ❌ </button>
                    </td>
                    <td>${order.part.from.run.company.name}</td>
                    <td>${order.part.from.run.number}</td>
                    <td>${order.part.from.station.name}</td>
                    <td>${order.part.from.departure}</td>
                    <td>${order.part.to.station.name}</td>
                    <td>${order.part.to.arrival}</td>
                    <td>${order.price}</td>
                    <td>${order.count}</td>
                    <td>${order.price * order.count}</td>
                </tr>
            </c:forEach>
        </table>
    </section>
</div>

<%@ include file="../footer.jsp" %>

</body>

</html>

