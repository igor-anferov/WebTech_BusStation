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
    <title>Добавление рейса</title>
</head>

<body>
<%@ include file="../header.jsp" %>
<div id="heading">
    <h1>Добавление рейса</h1>
</div>
<aside>
    <nav>
        <ul class="aside-menu">
            <li><a href="/">Информация о рейсах</a></li>
            <li class="active_submenu">Добавление рейса</li>
            <li><a href="/clients">Информация о клиентах</a></li>
            <li><a href="/stations">Информация о станциях</a></li>
            <li><a href="/companies">Информация о компаниях</a></li>
        </ul>
    </nav>
</aside>
<section>
    <h2>Маршрут</h2>
    <c:if test="${error_unfilled != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля</p>
    </c:if>
    <c:if test="${error_wrong_timeline != null}">
        <p class="error">Некорректно указан временной интервал</p>
    </c:if>
    <form name="set_stops" action="/runs/stops_set" method="post">
        <table class="autowidth" border="1">
            <tr>
                <th>
                    Станция
                </th>
                <th>
                    Время прибытия
                </th>
                <th>
                    Время отправления
                </th>
            </tr>
            <c:forEach var = "i" begin = "0" end = "${stops_count-1}">
                <tr>
                    <td>
                        <select title="Станция" name="selectedStations">
                            <option value="" <c:if test="${selectedStations == null || selectedStations.get(i) == null}"> selected</c:if> >-- Выберите станцию --</option>
                            <c:forEach items="${stationsList}" var="st">
                                <option value="${st.id}" <c:if test="${selectedStations != null && selectedStations.get(i) != null && selectedStations.get(i) == st.id}"> selected</c:if> >${st.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td <c:if test="${i == 0}"> class="passive" </c:if> >
                        <c:if test="${i != 0}">
                            <input name="arrival" type="datetime-local" <c:if test="${arrival != null && arrival.get(i-1) != null}"> value="${arrival.get(i-1)}" </c:if> >
                        </c:if>
                    </td>
                    <td <c:if test="${i == stops_count-1}"> class="passive" </c:if> >
                        <c:if test="${i != stops_count-1}">
                            <input name="departure" type="datetime-local" <c:if test="${departure != null && departure.get(i) != null}"> value="${departure.get(i)}" </c:if> >
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="hidden" name="stops_count" value="${stops_count}">
        <br>
        <button name="run_id" value="${run_id}" type="submit"> Далее ➡️</button>
    </form>
</section><%@ include file="../footer.jsp" %>
</body>

</html>

