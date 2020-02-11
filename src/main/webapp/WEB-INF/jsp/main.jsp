<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>
<html>

<body>
<div>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <input type="submit" value="Sign Out"/>
    </form>
</div>
<div>
    <!--    <form method="post">-->
    <!--        <input type="text" name="username" placeholder="Введите username"/>-->
    <!--        <input type="text" name="password" placeholder="Введите password"/>-->
    <!--        <input type="number" name="age" placeholder="Введите age"/>-->
    <!--        <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>-->
    <!--        <button type="submit">Добавить</button>-->
    <!--    </form>-->

</div>
<div>Список товаров</div>
<!--<form method="post" action="filter">-->
<!--    <input type="number" name="age" placeholder="age">-->
<!--    <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>-->
<!--    <button type="submit">Найти</button>-->
<!--</form>-->
<div>
    <table style="" border="2">
        <tr>
            <th>Product name</th>
            <th>Description</th>
            <th>Image</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <tbody>
        <c:forEach items="${products}" var="products">
        <tr>
            <td>${products.productName}</td>
            <td>${products.description}</td>
            <td>${products.image}</td>
            <td>${products.price}</td>
            <td>${products.quantity}</td>
            <td><a href="<c:url value='/addToCart/${products.id}'/>">Add to cart</a></td>
        <tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<table border="1" cellpadding="5" cellspacing="5">
    <tr>
        <c:if test="${(currentPage != 1) && (currentPage != null)}">
            <td><a href="main?page=${currentPage - 1}">Previous</a></td>
        </c:if>
        <c:forEach begin="1" end="${maxPage}" var="i">
            <c:choose>
                <c:when test="${currentPage eq i}">
                    <td>${i}</td>
                </c:when>
                <c:otherwise>
                    <td><a href="main?page=${i}">${i}</a></td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${(currentPage lt maxPage) || (currentPage == null)}">
            <td><a href="main?page=${currentPage + 1}">Next</a></td>
        </c:if>
    </tr>
</table>

<div>Корзина</div>
<div>
    <table style="" border="2">
        <tr>
            <th>Product name</th>
            <th>Description</th>
            <th>Image</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        <tbody>
        <c:forEach items="${productsInCart}" var="productsInCart">
            <tr>
                <td>${productsInCart.get("productName")}</td>
                <td>${productsInCart.get("productDescription")}</td>
                <td>${productsInCart.get("productImage")}</td>
                <td>${productsInCart.get("productPrice")}</td>
                <td>${productsInCart.get("productQuantity")}</td>
                <td><a href="<c:url value='/removeFromCart/${productsInCart.get("productId")}'/>">Remove from cart</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>