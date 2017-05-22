<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <h2>Поиск рейсов</h2>
    <form name="search" action="/runs/search" method="post">
        <label>Пункт отправления
            <select title="Пункт отправления" name="departure_point">
                <option value="null" <c:if test="${departure_point == null}"> selected</c:if> >-- Не выбрано --</option>
                <c:forEach items="${StationsList}" var="st">
                    <option value="${st.id}" <c:if test="${departure_point != null && departure_point == st.id}"> selected</c:if> >${st.name}</option>
                </c:forEach>
            </select>
        </label>
        <label>Пункт прибытия
            <select title="Пункт прибытия" name="arrival_point">
                <option value="null" <c:if test="${arrival_point == null}"> selected</c:if> >-- Не выбрано --</option>
                <c:forEach items="${StationsList}" var="st">
                    <option value="${st.id}" <c:if test="${arrival_point != null && arrival_point == st.id}"> selected</c:if> >${st.name}</option>
                </c:forEach>
            </select>
        </label>
        <div class="floatleft">
            <label>Промежуточные остановки</label>
        </div>
        <div class="container">
            <c:forEach items="${StationsList}" var="st">
                <label>
                    <input type="checkbox" name="intermediate_point" value="${st.id}" <c:if test="${intermediate_point != null && intermediate_point.contains(st.id)}">checked</c:if> >
                        ${st.name}
                </label>
            </c:forEach>
        </div>
        <div class="notfloat">
            <label>Дата отправления
                <input class="short" type="date" name="departure_date" placeholder="Любая" title="Дата отправления" <c:if test="${departure_date != null}">value="${departure_date}"</c:if> >
            </label>
            <label>Цена до
                <input class="short" type="number" name="upper_price" placeholder="Без ограничений" title="Цена до" <c:if test="${upper_price != null}">value="${upper_price}"</c:if> >
            </label>
            <label>Количество свободных мест
                <input class="short" type="number" name="free_places" placeholder="Без ограничений" title="Количество свободных мест" <c:if test="${free_places != null}">value="${free_places}"</c:if> >
            </label>
        </div>
        <br>
        <button type="submit"> Искать 🔎</button>
    </form>
    <table border="1">
        <tr>
            <th>Компания</th>
            <th>Номер рейса</th>
            <th>Пункт отправления</th>
            <th>Время отправления</th>
            <th>Пункт прибытия</th>
            <th>Время прибытия</th>
            <th>Цена</th>
            <th>Количество свободных мест</th>
            <th>Информация о рейсе</th>
            <th>Купить</th>
        </tr>
        <c:forEach items="${PartsList}" var="part">
            <tr>
                <td>${part.from.run.company.name}</td>
                <td>${part.from.run.number}</td>
                <td>${part.from.station.name}</td>
                <td>${part.from.departure}</td>
                <td>${part.to.station.name}</td>
                <td>${part.to.arrival}</td>
                <td>${part.price}</td>
                <td>${part.freeSeats}</td>
                <td>
                    <form name="watch_route" action="/runs/info" method="post">
                        <button class="watch" title="Информация о рейсе" name="run" value="${part.from.run.id}" type="submit"> 👁 </button>
                    </form>
                </td>
                <td>
                    <form name="buy_part" action="/runs/buy" method="post">
                        <button class="watch" title="Купить билет" name="run" value="${part.id}" type="submit"> 💸 </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
