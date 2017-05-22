<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <c:if test="${error != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля</p>
    </c:if>
    <form name="client_info_edit" action="/clients/edit_done" method="post">
        <label>
            Фамилия
            <input class="medium" type="text" name="lastName" placeholder="Необходимо заполнить это поле" <c:if test="${lastName != null}"> value="${lastName}" </c:if>>
        </label>
        <label>
            Имя
            <input class="medium" type="text" name="firstName" placeholder="Необходимо заполнить это поле" <c:if test="${firstName != null}"> value="${firstName}" </c:if>>
        </label>
        <label>
            Отчество
            <input class="medium" type="text" name="patronymic" <c:if test="${patronymic != null}"> value="${patronymic}" </c:if>>
        </label>
        <label>
            Адрес
            <input class="long" type="text" name="address" placeholder="Необходимо заполнить это поле" <c:if test="${address != null}"> value="${address}" </c:if>>
        </label>
        <label>
            Телефон
            <input class="short" type="text" name="telephone" placeholder="Необходимо заполнить это поле" <c:if test="${telephone != null}"> value="${telephone}" </c:if>>
        </label>
        <label>
            E-mail
            <input class="medium" type="text" name="email" placeholder="Необходимо заполнить это поле" <c:if test="${email != null}"> value="${email}" </c:if>>
        </label>
        <br>
        <button type="submit" <c:if test="${id != null}">name="id" value="${id}" </c:if>> Готово ✅</button>
    </form>
</section>