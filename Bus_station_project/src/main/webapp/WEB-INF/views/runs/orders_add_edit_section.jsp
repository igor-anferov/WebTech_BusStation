<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <h2>
        ${part.from.station.name} (${part.from.departure}) — ${part.to.station.name} (${part.to.arrival})
    </h2>
    <c:if test="${error_unfilled != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля</p>
    </c:if>
    <c:if test="${error_no_tickets != null}">
        <p class="error">Выбранное Вами количество билетов недоступно</p>
    </c:if>
    <form name="buy_tickets" action="/runs/buy_tickets" method="post">
        <input type="hidden" name="part" value="${part.id}"/>
        <label>
            Клиент
            <select title="Клиент" name="client">
                <c:if test="${client == null}">
                    <option value="null" selected disabled>-- Выберите клиента --</option>
                </c:if>
                    <c:forEach items="${ClientsList}" var="client_l">
                    <option value="${client_l.id}" <c:if test="${client != null && client == client_l.id}"> selected</c:if> >${client_l.lastName} ${client_l.firstName} ${client_l.patronymic}</option>
                </c:forEach>
            </select>
        </label>
        <label>Количество билетов
            <input class="medium" type="number" name="num_tickets" placeholder="Введите количество приобретаемых билетов" title="Количество билетов" <c:if test="${num_tickets != null}">value="${num_tickets}"</c:if> >
        </label>
        <br>
        <button type="submit" <c:if test="${id != null}">name="id" value="${id}" </c:if>> Готово ✅</button>
    </form>
</section>