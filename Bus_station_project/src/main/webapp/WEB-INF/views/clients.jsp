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
    <title>Информация о клиентах</title>
</head>

<body>
    <div id="wrapper">
        <a href="/">
            <header>
                <img src="<c:url value="/resources/images/logo.png" />" width="369" height="129" alt="Bus (logo)">
                <div id="title"><h1>Система управления автобусными рейсами и билетами</h1></div>
            </header>
        </a>
        <div id="heading">
            <h1>Информация о клиентах</h1>
        </div>
        <aside>
            <nav>
                <ul class="aside-menu">
                    <li><a href="/">Информация о рейсах</a></li>
                    <li class="active">Информация о клиентах</li>
                </ul>
            </nav>
        </aside>
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
                        <input class="medium" type="text" name="lastName" placeholder="Фамилия" <c:if test="${lastName != null}"> value="${lastName}" </c:if>>
                    </label>
                    <label>
                        Имя
                    <input class="medium" type="text" name="firstName" placeholder="Имя" <c:if test="${firstName != null}"> value="${firstName}" </c:if>>
                    </label>
                    <label>
                        Отчество
                    <input class="medium" type="text" name="patronymic" placeholder="Отчество" <c:if test="${patronymic != null}"> value="${patronymic}" </c:if>>
                    </label>
                    <label>
                        Адрес
                    <input class="long" type="text" name="address" placeholder="Адрес" <c:if test="${address != null}"> value="${address}" </c:if>>
                    </label>
                    <label>
                        Телефон
                    <input class="short" type="text" name="telephone" placeholder="Телефон" <c:if test="${telephone != null}"> value="${telephone}" </c:if>>
                    </label>
                    <label>
                        E-mail
                    <input class="medium" type="text" name="email" placeholder="E-mail" <c:if test="${email != null}"> value="${email}" </c:if>>
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
                            <button class="edit" title="Редактировать данные" type="submit"> 📝 </button>
                            <button class="edit" title="Удалить информацию о клиенте" type="submit"> ❌ </button>
                        </td>
                        <td>${client.lastName}</td>
                        <td>${client.firstName}</td>
                        <td>${client.patronymic}</td>
                        <td>${client.address}</td>
                        <td>${client.telephone}</td>
                        <td>${client.email}</td>
                        <td><a href="">Смотреть</a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </div>

    <div id="footer">
        <div id="copyright">
            <p>&copy; Анфёров Игорь Сергеевич, ВМК МГУ, 2017 </p>
        </div>
    </div>

</body>

</html>

