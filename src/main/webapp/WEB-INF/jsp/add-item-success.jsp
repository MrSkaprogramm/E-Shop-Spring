<%@ taglib prefix ="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>E shop add item success</title>
    <link href="${pageContext.request.contextPath}/style.css" rel="stylesheet" />
	<link href="${pageContext.request.contextPath}/css/glider.css" rel="stylesheet" />
</head>
<body>

<div id="popup4"  class="desktop-menu-popup">
            <a href="#header" class="desktop-menu_area"></a>
            <div class="desktop-menu_body">
                <div class="desktop-menu_content">
                    <ul class="shop-menu-list">
                        <li>
                            <form action="/item/showCatalog" method="post">
                                <input type="hidden" name="command" value="show_catalog"/>
                                <input type="submit" value="Каталог"/>
                            </form>
                            <a href="#">Каталог</a>
                            <hr class="hr-menu">
                        </li>
                        <li>
                            <form action="/item/showCatalog" method="post">
                                <input type="hidden" name="command" value="show_catalog"/>
                                <input type="submit" value="Каталог"/>
                            </form>
                            <a href="#">Скидки</a>
                            <hr class="hr-menu">
                        </li>
                        <li>
                            <form action="/item/showCatalog" method="post">
                                <input type="hidden" name="command" value="show_catalog"/>
                                <input type="submit" value="Каталог"/>
                            </form>
                            <a href="#">О магазине</a>
                            <hr class="hr-menu">
                        </li>
                        <li>
                            <form action="/item/showCatalog" method="post">
                                <input type="hidden" name="command" value="show_catalog"/>
                                <input type="submit" value="Каталог"/>
                            </form>
                            <a href="#">Контакты</a>
                            <hr class="hr-menu">
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <header>
            <div class="footer-nav">
                <div class="header-nav">
                    <div class="shop-menu-button">
                        <a href="#popup2" class="shop-menu-modal-open">
                            <picture>
                                <source media="(max-width: 760px)" srcset="./images/menu_button_tablet_mobile.svg">
                                <img src="./images/menu_button.svg" alt="Меню">
                            </picture>
                        </a>
                        <a href="#popup4" class="shop-menu2-modal-open">
                            <picture>
                                <source media="(max-width: 760px)" srcset="./images/menu_button_tablet_mobile.svg">
                                <img src="./images/menu_button.svg" alt="Меню">
                            </picture>
                        </a>
                    </div>
                    <div class="info">
                        <div class="info-info">
                            <span class="info-up">Наш адрес:<br /></span>
                            <span class="info-down">Минск, ул. Электроннокоммерческая, 1</span>
                        </div>
                        <div class="info-info">
                            <span class="info-up">Обработка заказов:<br /></span>
                            <span class="info-down">9:00-21:00 ежедневно</span>
                        </div>
                    </div>
                    <div class="logo">
                        <img src="images/logo.png" alt="E-shop"/>
                    </div>
                    <div class="info-phone">
                        <div class="phone-header">
                            <a href="tel:+375(29)642-80-80" class="phone-header-phone">+375(29)642-80-80</a>
                        </div>
                    </div>   
                    <div class="shopping-cart">
                        <form action="/user/identifyUser" class="input-auth" method="get">
                            <input type="hidden" name="command" value="identify_user"/>
                            <input type="submit" value=" "/>
                        </form>
                    </div> 
                    <div class="shopping-cart">
                        <form action="/cart/showCart" class="input-cart" method="post">
                            <input type="hidden" name="command" value="show_cart"/>
                            <input type="submit" value=" "/>
                        </form>
                    </div>
                </div>
            </div>
        </header>


<div id="header">
<h2>Новый товар</h2>
</div>
<div id="success">
<h2>Вы успешно добавили товар</h2>
	<form action="/E-Shop-Sfia/controller" method="get">
		<input type="hidden" name="command" value="go_admin_profile"/>
		<input type="submit" value="Перейти в меню администратора"/>
	</form>
	<form action="/item/showCatalog" method="post">
		<input type="hidden" name="command" value="show_catalog"/>
		<input type="submit" value="Перейти к каталогу товаров"/>
	</form>
</div>



<div class="footer-nav">
            <div class="logo">
                <img src="images/logo.png" alt="E-shop"/>
            </div>
            <div class="shop-menu-logo">
                <img src="images/logo.svg" alt="Lorem ipsum" />
            </div>

            <div class="info-footer">
                <div class="shop-menu-info-text">
                    <p class="shop-menu-info-up">Наш адрес:</p>
                    <p class="shop-menu-info-down">Минск, ул. Электроннокоммерческая, 1</p>
                </div>
                <div class="shop-menu-info-text">
                    <p class="shop-menu-info-up">Обработка заказов:</p>
                    <p class="shop-menu-info-down">9:00-21:00 ежедневно</p>
                </div>
                <div class="shop-menu-info-text">
                    <p class="shop-menu-info-up">E-mail:</p>
                    <p class="shop-menu-info-down">e-shop@gmail.com</p>
                </div>
            </div>
            <p>Телефоны:</p>
                <div class="info-phone-footer">
                    <div class="phone-modal">
                        <a href="tel:+375(29)612-34-56"><span class="phone-modal-first">+375 (29)&nbsp</span><span class="phone-modal-second">612-34-56</span></a>
                    </div>
                    <div class="phone-modal">
                        <a href="tel:+375(29)612-34-56"><span class="phone-modal-first">+375 (29)&nbsp</span><span class="phone-modal-second">623-45-56</span></a>
                    </div>
                    <div class="phone-modal">
                        <a href="tel:+375(29)612-34-56"><span class="phone-modal-first">+375 (29)&nbsp</span><span class="phone-modal-second">623-45-56</span></a>
                    </div>
                </div>
            </div>
            </div>
            <div class="payment_systems">
                <img src="images/visa.png" alt="Поиск">
                <img src="images/visa_secure.png" alt="Поиск">
                <img src="images/mastercard.png" alt="Поиск">
                <img src="images/mastercard_id_check.png" alt="Поиск">
                <img src="images/mir_accept.png" alt="Поиск">
                <img src="images/belkart.png" alt="Поиск">
                <img src="images/mir.png" alt="Поиск">
                <img src="images/belkart_internet_password.png" alt="Поиск">
                <img src="images/assist.png" alt="Поиск">
            </div>
        </div>
</body>
</html>