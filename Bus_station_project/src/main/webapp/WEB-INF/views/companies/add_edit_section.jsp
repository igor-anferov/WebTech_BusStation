<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <c:if test="${error != null}">
        <p class="error">Пожалуйста, заполните все обязательные поля!</p>
    </c:if>
    <form name="station_info_edit" action="/companies/edit_done" method="post">
        <label>
            Название
            <input class="medium" type="text" name="name" placeholder="Необходимо заполнить это поле" <c:if test="${name != null}"> value="${name}" </c:if>>
        </label>
        <br>
        <button type="submit" <c:if test="${id != null}">name="id" value="${id}" </c:if>> Готово ✅</button>
    </form>
</section>