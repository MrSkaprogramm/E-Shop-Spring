package eshop.spring.tr.controller;

import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;
import eshop.spring.tr.models.Role;
import eshop.spring.tr.models.User;
import eshop.spring.tr.service.ServiceException;
import eshop.spring.tr.service.impl.ItemService;
import eshop.spring.tr.service.impl.OrderService;
import eshop.spring.tr.service.impl.PaymentService;
import eshop.spring.tr.service.impl.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RestController
@RequestMapping("/")
public class Controller {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    static Scanner scanner = new Scanner(System.in);

    private final ItemService itemService;
    private final OrderService orderService;
    private final PaymentService paymentService;
    private final UserService userService;

    @Autowired
    public Controller(ItemService itemService, OrderService orderService, PaymentService paymentService, UserService userService) {
        this.itemService = itemService;
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "forward:/index.html";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam String name,
                          @RequestParam String itemInfo,
                          @RequestParam String price, // price теперь строка
                          Model model) {
        try {
            int itemPrice = Integer.parseInt(price); // Конвертация строки в int
            itemService.addItem(name, itemInfo, itemPrice);
            return "addItemSuccess"; // Имя JSP страницы для успешного добавления
        } catch (NumberFormatException e) {
            return "wrongData"; // Обработка ошибки конвертации
        } catch (ServiceException e) {
            if ("Wrong item data".equals(e.getMessage())) {
                return "wrongData"; // Имя JSP страницы для ошибки данных
            }
            LOGGER.error("Error while adding item", e);
            return "error"; // Имя JSP страницы для общего сообщения об ошибке
        }
    }

    @PostMapping("/addItemToCart")
    public String addItemToCart(@RequestParam String itemId,
                                @RequestParam String name,
                                @RequestParam String itemInfo,
                                @RequestParam String price,
                                HttpSession session,
                                Model model) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        try {
            int parsedItemId = Integer.parseInt(itemId); // Конвертация строки в int
            int parsedPrice = Integer.parseInt(price); // Конвертация строки в int

            cart = orderService.addItemToCart(cart, parsedItemId, name, itemInfo, parsedPrice);
            session.setAttribute("cart", cart);
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid item ID or price format", e);
            model.addAttribute("errorMessage", "Invalid item ID or price format.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }

        return "addItemToCartPage"; // Имя JSP страницы для добавления товара в корзину
    }

    @PostMapping("/addToBlacklist")
    public String addToBlacklist(@RequestParam String id, Model model) {
        try {
            int userId = Integer.parseInt(id); // Конвертация строки в int
            userService.addtoBlackList(userId);
            return "addToBlacklistSuccess"; // Имя JSP страницы для успешного добавления
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid user ID format", e);
            model.addAttribute("errorMessage", "Invalid user ID format.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        } catch (ServiceException e) {
            LOGGER.error("Service error while adding to blacklist", e);
            model.addAttribute("errorMessage", "Could not add to blacklist.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @PostMapping("/authorize")
    public String authorize(@RequestParam String login,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {
        try {
            User user = userService.userAuth(login, password);
            List<Item> cart = new ArrayList<>();

            if (user != null) {
                session.setAttribute("user", user);
                session.setAttribute("cart", cart);
                model.addAttribute("user", user);

                if (user.getRole() == Role.CLIENT) {
                    return "userClientProfile"; // Имя JSP страницы для клиента
                } else {
                    return "userAdminProfile"; // Имя JSP страницы для администратора
                }
            } else {
                model.addAttribute("errorMessage", "Wrong authorization data");
                return "wrongData"; // Имя JSP страницы для ошибки авторизации
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            model.addAttribute("errorMessage", "An error occurred during authorization.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @PostMapping("/changeItemDetails")
    public String changeItemDetails(@RequestParam String itemId,
                                    @RequestParam String itemInfo,
                                    @RequestParam String price,
                                    Model model) {
        try {
            int parsedItemId = Integer.parseInt(itemId); // Конвертация строки в int
            int parsedPrice = Integer.parseInt(price); // Конвертация строки в int

            itemService.changeItemsDetails(parsedItemId, itemInfo, parsedPrice);
            return "changeItemDetailsSuccess"; // Имя JSP страницы для успешного изменения
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid item ID or price format", e);
            model.addAttribute("errorMessage", "Invalid item ID or price format.");
            return "wrongData"; // Имя JSP страницы для ошибки данных
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            if ("Wrong item data".equals(e.getMessage())) {
                model.addAttribute("errorMessage", "Wrong item data.");
                return "wrongData"; // Имя JSP страницы для ошибки
            }
            model.addAttribute("errorMessage", "An error occurred while changing item details.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam String itemId, Model model) {
        try {
            int parsedItemId = Integer.parseInt(itemId); // Конвертация строки в int
            boolean itemDeleted = itemService.deleteItem(parsedItemId);

            if (itemDeleted) {
                return "deleteItemSuccess"; // Имя JSP страницы для успешного удаления
            } else {
                return "deleteItemDenied"; // Имя JSP страницы для отказа в удалении
            }
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid item ID format", e);
            model.addAttribute("errorMessage", "Invalid item ID format.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            model.addAttribute("errorMessage", "An error occurred while deleting the item.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @PostMapping("/deleteItemFromCart")
    public String deleteItemFromCart(@RequestParam String itemId, HttpSession session, Model model) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        try {
            int parsedItemId = Integer.parseInt(itemId); // Конвертация строки в int
            cart = orderService.deleteItemFromCart(cart, parsedItemId);
            session.removeAttribute("cart");
            session.setAttribute("cart", cart);
            return "deleteItemFromCartSuccess"; // Имя JSP страницы для успешного удаления
        } catch (NumberFormatException e) {
            LOGGER.error("Invalid item ID format", e);
            model.addAttribute("errorMessage", "Invalid item ID format.");
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @GetMapping("/exit")
    public String exit(HttpSession session) {
        try {
            session.invalidate(); // Удаляем сессию
            return "userAuth"; // Имя JSP страницы для авторизации
        } catch (Exception e) {
            LOGGER.error("Error during logout", e);
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @PostMapping("/makeOrder")
    public String makeOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        if (user == null || cart == null || cart.isEmpty()) {
            return "wrongOrder"; // Имя JSP страницы для обработки ошибок
        }

        int userId = user.getUserId();
        boolean isOrdered;

        try {
            isOrdered = orderService.makeOrder(userId, cart);

            if (isOrdered) {
                return "makeOrderSuccess"; // Имя JSP страницы для успешного заказа
            } else {
                return "wrongOrder"; // Имя JSP страницы для обработки ошибок
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        } finally {
            // Очистка корзины после обработки заказа
            cart.clear();
            session.removeAttribute("cart");
            session.setAttribute("cart", cart);
        }
    }

    @PostMapping("/makePayment")
    public String makePayment(@RequestParam int orderId,
                              @RequestParam String bankCardNum,
                              @RequestParam String expiringDate,
                              Model model) {
        try {
            paymentService.makePayment(orderId, bankCardNum, expiringDate);
            return "paymentSuccess"; // Имя JSP страницы для успешного платежа
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            if ("Wrong payment data".equals(e.getMessage())) {
                return "wrongData"; // Имя JSP страницы для неправильных данных
            }
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @GetMapping("/error")
    public String handleNoSuchCommand() {
        // Логируем ошибку
        LOGGER.error("No such command found.");
        return "errorPage"; // Имя JSP страницы для отображения ошибки
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String login,
                               @RequestParam String password,
                               @RequestParam String fio,
                               @RequestParam String email,
                               @RequestParam String phoneNum,
                               @RequestParam String address,
                               @RequestParam int role,
                               Model model) {
        try {
            boolean isRegistered = userService.userRegistration(login, password, fio, email, phoneNum, address, role);
            if (isRegistered) {
                return "userRegisterSuccess"; // Имя JSP страницы для успешной регистрации
            } else {
                return "wrongData"; // Имя JSP страницы для неправильных данных
            }
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            if ("Wrong registration data".equals(e.getMessage())) {
                return "wrongData"; // Имя JSP страницы для неправильных данных
            }
            return "errorPage"; // Имя JSP страницы для обработки ошибок
        }
    }

    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        if (cart != null) {
            model.addAttribute("cart", cart);
        } else {
            model.addAttribute("cart", List.of()); // Пустая корзина, если нет элементов
        }

        return "cartPage"; // Имя JSP страницы для отображения корзины
    }

    @GetMapping("/catalog")
    public String showCatalog(HttpSession session, Model model) {
        List<Item> catalog;

        try {
            catalog = itemService.getAllItems();
            model.addAttribute("catalog", catalog);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            return "errorPage"; // Возвращаем страницу ошибки
        }

        User user = (User) session.getAttribute("user");

        if (user != null && user.getRole() == Role.CLIENT) {
            return "clientCatalogPage"; // Имя JSP страницы для клиентского каталога
        } else {
            return "adminCatalogPage"; // Имя JSP страницы для администраторского каталога
        }
    }

    @GetMapping("/orders")
    public String showOrderList(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Перенаправление на страницу входа, если пользователь не авторизован
        }

        int userId = user.getUserId();
        List<Order> orders;

        try {
            orders = orderService.getAllOrders(userId);
            model.addAttribute("orders", orders);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            return "errorPage"; // Возвращаем страницу ошибки в случае исключения
        }

        return "ordersListPage"; // Имя JSP страницы для отображения списка заказов
    }

    @GetMapping("/payment-evaders")
    public String showPaymentEvaderList(Model model) {
        List<User> paymentEvaders;

        try {
            paymentEvaders = userService.getAllPaymentEvaders();
            model.addAttribute("paymentEvaders", paymentEvaders);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage());
            return "errorPage"; // Возвращаем страницу ошибки в случае исключения
        }

        return "paymentEvadersPage"; // Имя JSP страницы для отображения списка уклонистов
    }
}