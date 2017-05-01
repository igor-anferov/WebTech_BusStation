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
        <title>Информация о рейсах</title>
    </head>

    <body>
        <%@ include file="header.jsp" %>
        <div id="heading">
            <h1>Информация о рейсах</h1>
        </div>
        <aside>
            <nav>
                <ul class="aside-menu">
                    <li class="active">Информация о рейсах</li>
                    <li><a href="clients">Информация о клиентах</a></li>
                </ul>
            </nav>
        </aside>
        <section>
            <h2>Поиск рейсов</h2>
            <form name="search" action="#" method="get">
                <label>Пункт отправления
                    <select title="Пункт отправления" name="departure_point">
                        <option selected>-- Не выбрано --</option>
                        <c:forEach items="${StationsList}" var="station">
                            <option>${station.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <label>Пункт прибытия
                    <select title="Пункт прибытия" name="arrival_point">
                        <option selected>-- Не выбрано --</option>
                        <c:forEach items="${StationsList}" var="station">
                            <option>${station.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <div class="notfloat">
                    <div class="floatleft">
                        <label>Промежуточные остановки</label>
                    </div>
                    <div class="container">
                        <c:forEach items="${StationsList}" var="station">
                            <label>
                                <input type="checkbox">
                                ${station.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>
                <div class="notfloat">
                    <label>Дата отправления
                        <input class="short" type="date" name="departure_date" placeholder="Любая" title="Дата отправления">
                    </label>
                    <label>Цена до
                        <input class="short" type="number" name="upper_price" placeholder="Без ограничений" title="Цена до">
                    </label>
                    <label>Количество свободных мест
                        <input class="short" type="number" name="free_places" placeholder="Без ограничений" title="Количество свободных мест">
                    </label>
                </div>
                <br>
                <button type="submit"> Искать 🔎</button>
            </form>
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
                    <th>Количество свободных мест</th>
                    <th>Маршрут</th>
                    <th>Купить</th>
                </tr>
                <c:forEach items="${PartsList}" var="part">
                    <tr>
                        <td>
                            <button class="edit" title="Редактировать информацию о рейсе" type="submit"> 📝 </button>
                            <button class="edit" title="Удалить рейс" type="submit"> ❌ </button>
                        </td>
                        <td>${part.from.run.company.name}</td>
                        <td>${part.from.run.number}</td>
                        <td>${part.from.station.name}</td>
                        <td>${part.from.departure}</td>
                        <td>${part.to.station.name}</td>
                        <td>${part.to.arrival}</td>
                        <td>${part.price}</td>
                        <td>${part.freeSeats}</td>
                        <td><a href="">Смотреть</a></td>
                        <td><a href="">Купить</a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
        <%@ include file="footer.jsp" %>
    </body>

</html>