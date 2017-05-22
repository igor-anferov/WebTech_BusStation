<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <c:if test="${error_unfilled != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля</p>
    </c:if>
    <c:if test="${error_wrong_timeline != null}">
        <p class="error">Некорректно указан временной интервал</p>
    </c:if>
    <form name="stop_edit" action="/runs/stops/edit_done" method="post">
        <input type="hidden" name="run_id" value="${run_id}"/>
        <select title="Станция" name="station">
            <c:if test="${station == null}">
                <option disabled value="null" <c:if test="${stop == null}"> selected</c:if> >Необходимо выбрать станцию</option>
            </c:if>
            <c:forEach items="${StationsList}" var="st">
                <option value="${st.id}" <c:if test="${station != null && station == st.id}"> selected</c:if> >${st.name}</option>
            </c:forEach>
        </select>
        <c:if test="${stop == null || stop.arrival != null}">
            <label>Время прибытия
                <input class="medium" type="datetime-local" name="arrival" placeholder="Необходимо заполнить это поле" title="Время прибытия" <c:if test="${arrival != null}">value="${arrival}"</c:if> >
            </label>
        </c:if>
        <c:if test="${stop == null || stop.departure != null}">
            <label>Время отправления
                <input class="medium" type="datetime-local" name="departure" placeholder="Необходимо заполнить это поле" title="Время отправления" <c:if test="${departure != null}">value="${departure}"</c:if> >
            </label>
        </c:if>
        <br>
        <button type="submit" <c:if test="${stop != null}">name="stop_id" value="${stop.id}" </c:if>> Готово ✅</button>
    </form>
</section>