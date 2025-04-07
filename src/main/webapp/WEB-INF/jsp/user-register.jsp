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
<h2>Регистрация</h2>
</div>
<div id="registration">
	<form action="/user/register" method="post">
		<input type="hidden" name="command" value="registration"/>
		<h3>Введите логин:</h3>
		<input type="text" name="login" value=""/>
		<h3>Введите пароль:</h3>
		<input type="password" name="pass" value=""/>
		<h3>Введите фамилию, имя и отчество:</h3>
		<input type="text" name="fio" value=""/>
		<h3>Введите e-mail:</h3>
		<input type="text" name="email" value=""/>
		<h3>Введите адрес доставки:</h3>
		<input type="text" name="address" value=""/>
		<h3>Выберите тип аккаунта пользователя:</h3>
		<select name="role">
			<option value="1">Клиент</option>
			<option value="2">Админ</option>
		</select>
		<br>
		<br>
		<input type="submit" value="Зарегистрироваться"/>
	</form>
</div>
<div id="footer">Интернет-магазин E-SHOP-SFIA®</div>
</body>
</html>