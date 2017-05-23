<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <c:if test="${error_unfilled != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля</p>
    </c:if>
    <c:if test="${error_duplicate_number != null}">
        <p class="error">Рейс с таким номером уже существует</p>
    </c:if>
    <c:if test="${error_low_bus_capacity != null}">
        <p class="error">Невозможно уменьшить вместимость автобуса до выбранного значения, так как билеты уже проданы</p>
    </c:if>
    <c:if test="${error_negative_bus_capacity != null}">
        <p class="error">Вместимость автобуса не может быть отрицательной</p>
    </c:if>
    <c:if test="${error_low_stops_count != null}">
        <p class="error">Количество остановок не может быть меньше двух</p>
    </c:if>
    <form name="stop_edit" action="/runs/edit_done" method="post">
        <label>Компания
            <select title="Компания" name="company">
                <c:if test="${company == null}">
                    <option disabled value="null" <c:if test="${run_id == null}"> selected</c:if> >Выберите компанию</option>
                </c:if>
                <c:forEach items="${CompaniesList}" var="comp">
                    <option value="${comp.id}" <c:if test="${company == comp.id}"> selected</c:if> >${comp.name}</option>
                </c:forEach>
            </select>
        </label>
        <label>Номер рейса
            <input class="medium" type="text" name="run_number" placeholder="Необходимо заполнить это поле" title="Номер рейса" value="${run_number}">
        </label>
        <label>Вместимость автобуса
            <input class="short" type="number" min="0" step="1" name="bus_capacity" placeholder="Необходимо заполнить это поле" title="Вместимость автобуса" value="${bus_capacity}">
        </label>
        <c:if test="${run_id == null}">
            <label>Количество остановок (включая пункт отправления и пункт прибытия)
                <input class="short" type="number" min="2" step="1" name="stops_count" placeholder="Необходимо заполнить это поле" title="Количество остановок" value="${stops_count}">
            </label>
        </c:if>
        <br>
        <c:if test="${run_id != null}">
            <button type="submit" name="run_id" value="${run_id}"> Готово ✅</button>
        </c:if>
        <c:if test="${run_id == null}">
            <button type="submit"> Далее ➡️</button>
        </c:if>
    </form>
</section>