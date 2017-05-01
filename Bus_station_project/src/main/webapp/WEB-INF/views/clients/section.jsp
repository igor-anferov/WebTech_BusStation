<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<section>
    <div>
        <h2>Поиск по номеру рейса</h2>
        <form name="search_by_run_number" method="post" action="/clients/search_by_run_number">
            <input class="short" type="text" name="run_number" placeholder="Номер рейса" <c:if test="${run_number != null}"> value="${run_number}" </c:if>>
            <button type="submit"> Искать 🔎</button>
        </form>
    </div>
    <div>
        <h2>Поиск по названию компани, выполняющей рейс</h2>
        <form name="search_by_company" action="/clients/search_by_company" method="post">
            <input class="medium" type="text" name="company" placeholder="Название компании" <c:if test="${company != null}"> value="${company}" </c:if>>
            <button type="submit"> Искать 🔎</button>
        </form>
    </div>
    <div>
        <h2>Поиск по персональным данным</h2>
        <form name="search_by_private_information" action="/clients/search_by_private_information" method="post">
            <label>
                Фамилия
                <input class="medium" type="text" name="lastName" placeholder="Любая" <c:if test="${lastName != null}"> value="${lastName}" </c:if>>
            </label>
            <label>
                Имя
                <input class="medium" type="text" name="firstName" placeholder="Любое" <c:if test="${firstName != null}"> value="${firstName}" </c:if>>
            </label>
            <label>
                Отчество
                <input class="medium" type="text" name="patronymic" placeholder="Любое" <c:if test="${patronymic != null}"> value="${patronymic}" </c:if>>
            </label>
            <label>
                Адрес
                <input class="long" type="text" name="address" placeholder="Любой" <c:if test="${address != null}"> value="${address}" </c:if>>
            </label>
            <label>
                Телефон
                <input class="short" type="text" name="telephone" placeholder="Любой" <c:if test="${telephone != null}"> value="${telephone}" </c:if>>
            </label>
            <label>
                E-mail
                <input class="medium" type="text" name="email" placeholder="Любой" <c:if test="${email != null}"> value="${email}" </c:if>>
            </label>
            <label><input type="checkbox" name="order_history" title="С историей заказов" <c:if test="${order_history != null && order_history.equals(true)}"> checked </c:if>>С историей заказов</label>
            <br>
            <button type="submit"> Искать 🔎</button>
        </form>
    </div>
    <table border="1">
        <tr>
            <th>Редактировать</th>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Адрес</th>
            <th>Телефон</th>
            <th>E-mail</th>
            <th>Информация о покупках</th>
        </tr>
        <c:forEach items="${ClientsList}" var="client">
            <tr>
                <td>
                    <form name="edit_client" id="edit_client_form" action="/clients/edit" method="post">
                        <button class="edit" title="Редактировать данные" name="client" value="${client.id}" type="submit"> 📝 </button>
                    </form>
                    <form name="remove_client" id="remove_client_form" action="/clients/rm" method="post">
                        <button class="edit" title="Удалить информацию о клиенте" name="client" value="${client.id}" type="submit"> ❌ </button>
                    </form>
                </td>
                <td>${client.lastName}</td>
                <td>${client.firstName}</td>
                <td>${client.patronymic}</td>
                <td>${client.address}</td>
                <td>${client.telephone}</td>
                <td>${client.email}</td>
                <td>
                    <form name="view_orders" id="view_orders_form" action="/clients/orders" method="post">
                        <button class="watch" title="Смотреть" name="client" value="${client.id}" type="submit"> 👁 </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>
