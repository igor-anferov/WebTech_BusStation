<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <h2>Стоимость билетов для разных частей маршрута</h2>
    <form name="edit_stop" id="edit_stop_form" action="/runs/parts/edit_done" method="post">
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
                        <td <c:if test="${i>=j}"> class="passive short" </c:if> >
                            <c:if test="${i<j}">
                                <input type="hidden" name="stops_from" value="${StopsList.get(i).id}">
                                <input type="hidden" name="stops_to" value="${StopsList.get(j).id}">
                                <input type="hidden" name="parts_list" value="${PartsTable.get(i).get(j).id}">
                                <input class="short" type="number" min="0" step="1" name="priceList" title="Цена" value="${PartsTable.get(i).get(j).price}">
                            </c:if>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
        <p>
            ⚠️ Продажа билетов на части маршрута с незаполненной ценой билета будет невозможна
        </p>
        <br/>
        <button type="submit" name="run_id" value="${run_id}"> Готово ✅</button>
    </form>
</section>