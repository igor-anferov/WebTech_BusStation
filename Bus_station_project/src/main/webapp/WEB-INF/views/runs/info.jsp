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
        <title>Информация о рейсе ${run.number}</title>
    </head>

    <body>
        <%@ include file="../header.jsp" %>
        <div id="heading">
            <h1>Информация о рейсе ${run.number}</h1>
        </div>
        <aside>
            <nav>
                <ul class="aside-menu">
                    <li class="active"><a href="/">Информация о рейсах</a></li>
                    <li class="submenu"><a href="/runs/add">Добавление рейса</a></li>
                    <li><a href="/clients">Информация о клиентах</a></li>
                    <li><a href="/stations">Информация о станциях</a></li>
                    <li><a href="/companies">Информация о компаниях</a></li>
                </ul>
            </nav>
        </aside>
        <section>
            <h2>Компания: ${run.company.name}</h2>
            <h2>Номер рейса: ${run.number}</h2>
            <h2>Вместимость автобуса: ${run.busCapacity}</h2>
            <form name="edit_run" id="edit_run_form" action="/runs/edit" method="post">
                <button class="single_edit" title="Изменить информацию о рейсе" name="run_id" value="${run.id}" type="submit">Изменить информацию о рейсе</button>
            </form>
            <form name="remove_run" id="remove_run_form" action="/runs/rm" method="post">
                <button class="single_rm" title="Удалить рейс" name="run_id" value="${run.id}" type="submit">Удалить рейс</button>
            </form>
            <h2>Маршрут</h2>
            <table class="autowidth" border="1">
                <tr>
                    <th>Редактировать</th>
                    <th>Станция</th>
                    <th>Время прибытия</th>
                    <th>Время отправления</th>
                </tr>
                <c:forEach items="${StopsList}" var="stop">
                    <tr>
                        <td>
                            <form name="edit_stop" id="edit_stop_form" action="/runs/stops/edit" method="post">
                                <input type="hidden" name="run_id" value="${run.id}"/>
                                <button class="edit" title="Редактировать данные" name="stop_id" value="${stop.id}" type="submit"> 📝 </button>
                            </form>
                            <c:if test="${stop.arrival != null && stop.departure != null}">
                                <form name="remove_stop" id="remove_stop_form" action="/runs/stops/rm" method="post">
                                    <button class="edit" title="Удалить остановку" name="stop_id" value="${stop.id}" type="submit"> ❌ </button>
                                </form>
                            </c:if>
                        </td>
                        <td>${stop.station.name}</td>
                        <td <c:if test="${stop.arrival == null}"> class="passive" </c:if> >${stop.arrival}</td>
                        <td <c:if test="${stop.departure == null}"> class="passive" </c:if> >${stop.departure}</td>
                    </tr>
                </c:forEach>
            </table>
            <form name="add_stop" id="add_stop_form" action="/runs/stops/add" method="post">
                <button class="add" title="Добавить остановку" name="run_id" value="${run.id}" type="submit">Добавить остановку</button>
            </form>

            <h2>Доступность и стоимость билетов</h2>
            <table class="autowidth" border="1">
                <tr>
                    <th>
                        <div class="wrap">
                            <div><span>В</span></div>
                            <div><span>Из</span></div>
                        </div>
                    </th>
                    <c:forEach items="${StopsList}" var="stop">
                        <th>${stop.station.name}</th>
                    </c:forEach>
                </tr>
                <c:forEach var = "i" begin = "0" end = "${StopsList.size()-1}">
                    <tr>
                        <th>${StopsList.get(i).station.name}</th>
                        <c:forEach var = "j" begin = "0" end = "${StopsList.size()-1}">
                            <td <c:if test="${i>=j}"> class="passive" </c:if> >${PartsTable.get(i).get(j).price}</td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </table>
            <form name="edit_parts" id="edit_parts_form" action="/runs/parts/edit" method="post">
                <button class="single_edit" title="Изменить доступность частей маршрута и стоимость билетов" name="run_id" value="${run.id}" type="submit">Изменить доступность частей маршрута и стоимость билетов</button>
            </form>

        </section>
        <%@ include file="../footer.jsp" %>
    </body>
</html>
