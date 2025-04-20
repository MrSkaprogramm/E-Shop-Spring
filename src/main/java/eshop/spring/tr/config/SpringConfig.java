package eshop.spring.tr.config;

import eshop.spring.tr.dao.impl.ItemDao;
import eshop.spring.tr.dao.impl.OrderDao;
import eshop.spring.tr.dao.impl.PaymentDao;
import eshop.spring.tr.dao.impl.UserDao;
import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;
import eshop.spring.tr.models.User;
import eshop.spring.tr.service.impl.ItemService;
import eshop.spring.tr.service.impl.OrderService;
import eshop.spring.tr.service.impl.PaymentService;
import eshop.spring.tr.service.impl.UserService;
import eshop.spring.tr.service.validation.impl.ItemValidator;
import eshop.spring.tr.service.validation.impl.PaymentValidator;
import eshop.spring.tr.service.validation.impl.UserValidator;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"eshop.spring.tr.dao", "eshop.spring.tr.model", "eshop.spring.tr.service",
        "eshop.spring.tr"})
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;

    @Value("${db.driver}")
    private String connectionDriverClass;

    @Value("${db.url}")
    private String connectionUrl;

    @Value("${db.user}")
    private String connectionUsername;

    @Value("${db.password}")
    private String connectionPassword;

    @Value("${db.poolsize}")
    private int poolSize;

    @Bean
    public ItemService itemService(ItemDao itemDao, ItemValidator itemValidator) {
        return new ItemService(itemDao, itemValidator);
    }

    @Bean
    public OrderService orderService(OrderDao orderDao, UserDao userDao) {
        return new OrderService(orderDao, userDao);
    }

    @Bean
    public PaymentService paymentService(PaymentDao paymentDao, PaymentValidator paymentValidator) {
        return new PaymentService(paymentDao, paymentValidator);
    }

    @Bean
    public UserService userService(UserDao userDao, UserValidator userValidator) {
        return new UserService(userDao, userValidator);
    }

    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();

        configuration.setProperty("hibernate.connection.driver_class", connectionDriverClass);
        configuration.setProperty("hibernate.connection.url", connectionUrl);
        configuration.setProperty("hibernate.connection.username", connectionUsername);
        configuration.setProperty("hibernate.connection.password", connectionPassword);
        configuration.setProperty("hibernate.connection.pool_size", String.valueOf(poolSize));
        configuration.setProperty("hibernate.dialect", hibernateDialect);
        configuration.setProperty("hibernate.show_sql", hibernateShowSql);

        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Order.class);
        configuration.addAnnotatedClass(Item.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }
}
