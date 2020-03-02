<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>
<head>
    <title>iShop личный кабинет</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<body>
<div class="container mt-5">
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit"><h5>Выход</h5></button>
    </form>
    <h5>Приветствуем Вас, ${username}!</h5>
</div>
<div class="container mt-5"><h5>Список товаров</h5>
    <form action="/main" method="get">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="text" name="productName" placeholder="Название товара">
        <button class="btn btn-primary" type="submit">Найти</button>
    </form>
</div>
<div class="container mt-5">
    <table style="" border="0">
        <tr>
            <c:if test="${isAdmin}">
                <th>Id</th>
            </c:if>
            <th>
                <div>Название товара</div>
                <div>Отсориторовать:
                    <div><a href="main?page=${currentPage}&sort=${"productNameAsc"}"><h2>> </h2></a></div>
                    <div><a href="main?page=${currentPage}&sort=${"productNameDesc"}"><h2>< </h2></a></div>
                </div>
            </th>
            <th>Описание</th>
            <th>Изображение</th>
            <th>
                <div>Цена</div>
                <div>Отсортировать:
                    <div><a href="main?page=${currentPage}&sort=${"priceAsc"}"><h2>> </h2></a></div>
                    <div><a href="main?page=${currentPage}&sort=${"priceDesc"}"><h2>< </h2></a></div>
                </div>
            </th>
            <th>Количество в наличии</th>
        </tr>
        <tbody>
        <c:forEach items="${products}" var="products">
        <tr>
            <c:if test="${isAdmin}">
            <td>${products.id}</td>
            </c:if>
            <td>${products.productName}</td>
            <td>${products.description}</td>
            <td><img src="${products.image}" width="310" height="200"></td>
            <td>${products.price}</td>
            <td>
                <center>${products.quantity}</center>
            </td>
            <c:if test="${!isAdmin}">
                <td><a href="<c:url value='/addToCart/${products.id}'/>"><h2>+</h2></a></td>
            </c:if>
            <c:if test="${isAdmin}">
            <td><a href="<c:url value='/removeProduct/${products.id}'/>">Remove product</a></td>
            </c:if>
        <tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<br>
<center>
    <div class="container mt-2">
        <table border="0" cellpadding="5" cellspacing="5">
            <tr>
                <c:if test="${(currentPage != 1) && (currentPage != null)}">
                    <td><a href="main?page=${currentPage - 1}&sort=${sort}"><h5>Предыдущая</h5></a></td>
                </c:if>
                <c:choose>
                    <c:when test="${(currentPage == maxPage) && (maxPage > 5)}">
                        <c:forEach begin="${maxPage - 4}" end="${maxPage}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td><h5>${i}</h5></td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="main?page=${i}&sort=${sort}"><h5>${i}</h5></a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                    <c:when test="${(currentPage >= 5) && (currentPage <= maxPage)}">
                        <c:forEach begin="${1 + (currentPage - 4)}" end="${1 + (currentPage - 4) + 4}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <td><h5>${i}</h5></td>
                                </c:when>
                                <c:otherwise>
                                    <td><a href="main?page=${i}&sort=${sort}"><h5>${i}</h5></a></td>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${maxPage < 5}">
                            <c:forEach begin="1" end="${maxPage}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <td><h5>${i}</h5></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><a href="main?page=${i}&sort=${sort}"><h5>${i}</h5></a></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                        <c:if test="${maxPage >= 5}">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <td><h5>${i}</h5></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><a href="main?page=${i}&sort=${sort}"><h5>${i}</h5></a></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:if>
                    </c:otherwise>
                </c:choose>
                <c:if test="${(currentPage lt maxPage) || (currentPage == null)}">
                    <td><a href="main?page=${currentPage + 1}&sort=${sort}"><h5>Следующая</h5></a></td>
                </c:if>
            </tr>
        </table>
    </div>
</center>
<br>
<div class="container mt-5">
    <c:if test="${!isAdmin}">
    <c:if test="${sizeCart != 0}">
    <h5 class="container mt-5"><h4>Корзина</h4>
        <div>
            <table style="" border="0">
                <tr>
                    <th>Название товара</th>
                    <th>Описание товара</th>
                    <th>Изображение</th>
                    <th>Цена</th>
                    <th>Количество</th>
                </tr>
                <tbody>
                <c:forEach items="${productsInCart}" var="productsInCart">
                    <tr>
                        <td>${productsInCart.get("productName")}</td>
                        <td>${productsInCart.get("productDescription")}</td>
                        <td><img src="${productsInCart.get("productImage")}" width="310" height="200"></td>
                        <td>${productsInCart.get("productPrice")}</td>
                        <td>
                            <center>${productsInCart.get("productQuantity")}</center>
                        </td>
                        <td><a href="<c:url value='/removeFromCart/${productsInCart.get("productId")}'/>"><h2>-</h2></a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="container mt-3">
            <form action="<c:url value="/buy"/>">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="btn btn-primary" type="submit"><h5>Купить</h5></button>
            </form>
        </div>
        </c:if>
        </c:if>
</div>
<c:if test="${isAdmin}">
    <div class="container mt-5">
    <form action="<c:url value="/update"/>">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="number" name="id" placeholder="id*(necessarily)"/>
    <input type="text" name="productName" placeholder="Product name"/>
    <input type="text" name="description" placeholder="Description"/>
    <input type="text" name="image" placeholder="Image"/>
    <input type="number" name="price" placeholder="Price"/>
    <input type="number" name="quantity" placeholder="Quantity"/>
    <button class="btn btn-primary" type="submit">Update product</button>
    </form>
    </div>
    <br>
    <div class="container mt-5">
        <form action="<c:url value="/add"/>">
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <input type="text" name="productName" placeholder="Product name"/>
    <input type="text" name="description" placeholder="Description"/>
    <input type="text" name="image" placeholder="Image"/>
    <input type="number" name="price" placeholder="Price"/>
    <input type="number" name="quantity" placeholder="Quantity"/>
    <button class="btn btn-primary" type="submit">Add product</button>
        </form>
    </div>
</c:if>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>