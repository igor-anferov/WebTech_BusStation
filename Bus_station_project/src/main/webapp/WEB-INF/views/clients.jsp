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
            <p>Поиск по номеру рейса</p>
            <form name="search" action="#" method="get">
                <input type="text" name="q" placeholder="Номер рейса">
                <button type="submit"> Искать 🔎</button>
            </form>
        </div>
        <div>
            <p>Поиск по названию компани, выполнявшей рейс</p>
            <form name="search" action="#" method="get">
                <input type="text" name="q" placeholder="Название компании">
                <button type="submit"> Искать 🔎</button>
            </form>
        </div>
        <div>
            <p>Поиск по персональным данным</p>
            <form name="search" action="#" method="get">
                <input type="text" name="q" placeholder="Фамилия">
                <input type="text" name="q" placeholder="Имя">
                <input type="text" name="q" placeholder="Отчество">
                <br>
                <input type="text" name="q" placeholder="Адрес">
                <input type="text" name="q" placeholder="Телефон">
                <input type="text" name="q" placeholder="E-mail">
                <br>
                <label><input type="checkbox" name="q" title="С историей заказов">С историей заказов</label>
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
</body>

<footer>
    <div id="footer">
        <div id="footer-logo">
            <p>&copy; Анфёров Игорь Сергеевич, ВМК МГУ, 2017 </p>
        </div>
    </div>
</footer>

</html>

