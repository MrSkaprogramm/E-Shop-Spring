<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E shop add item</title>
    <link href="${pageContext.request.contextPath}/style.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/css/glider.css" rel="stylesheet" />
</head>
<body>
<div id="header">
Удалить товар
</div>
<div id="success">
<h2>Этот товар уже заказан клиентами. Вы не можете его удалить</h2>
	<form action="/item/showCatalog" method="post">
		<input type="hidden" name="command" value="show_catalog"/>
		<input type="submit" value="Перейти к каталогу товаров"/>
	</form>
</div>
<div id="footer">Интернет-магазин E-SHOP-SFIA®</div>
</body>
</html>