package eshop.spring.tr.dao.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.OrderDaoInterface;
import eshop.spring.tr.models.Item;
import eshop.spring.tr.models.Order;
import eshop.spring.tr.models.Payment;
import eshop.spring.tr.service.ServiceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDao implements OrderDaoInterface {
    //private static final ConnectionPool connectionPool = ConnectionPool.getConnectionPool();
    private final SessionFactory sessionFactory;

    private static final int ORDER_ID_COLUMN_INDEX = 1;
    private static final int ORDER_STATUS_COLUMN_INDEX = 2;
    private static final int ORDER_PAYMENT_SUM_COLUMN_INDEX = 3;
    private static final String GET_ORDERS_QUERY =
            "SELECT orders.id, orders.order_status, payments.payment_sum FROM eshopdb.orders, payments "
                    + "WHERE orders.id=payments.orders_id AND orders.users_id=?";
    private static final String ADD_ORDER_QUERY =
            "INSERT INTO orders(order_status, users_id) VALUES (false, ?);";
    private static final String FIND_LAST_ORDER_QUERY =
            "SELECT * FROM orders WHERE users_id =? ORDER BY id DESC;";
    private static final String ADD_ITEMS_TO_ORDER_QUERY =
            "INSERT INTO orders_has_items VALUES(?, ?);";
    private static final String UPDATE_ORDER_PAYMENT_SUM_QUERY =
            "INSERT INTO payments(orders_id, payment_sum) VALUES(?, ?);";

    public OrderDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Method of placing an order
     *
     */
    @Override
    public void makeOrder(int userId, List<Item> cart) throws ServiceException, DaoException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Создание нового заказа
            session.createNativeQuery(ADD_ORDER_QUERY)
                    .setParameter(1, userId)
                    .executeUpdate();

            // Получение ID последнего заказа
            int orderId = (int) session.createNativeQuery(FIND_LAST_ORDER_QUERY)
                    .setParameter(1, userId)
                    .getSingleResult();

            int paymentSum = 0;

            // Добавление товаров в заказ
            for (Item item : cart) {
                session.createNativeQuery(ADD_ITEMS_TO_ORDER_QUERY)
                        .setParameter(1, orderId)
                        .setParameter(2, item.getItemId())
                        .executeUpdate();
                paymentSum += item.getPrice();
            }

            // Сохранение суммы платежа
            session.createNativeQuery(UPDATE_ORDER_PAYMENT_SUM_QUERY)
                    .setParameter(1, orderId)
                    .setParameter(2, paymentSum)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(e.getMessage(), e);
        }
    }

    /**
     *
     * Method of receiving all orders
     *
     */
    @Override
    public List<Order> getAllOrders(int userId) throws DaoException {
        List<Order> orders = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            List<Object[]> results = session.createNativeQuery(GET_ORDERS_QUERY)
                    .setParameter("userId", userId)
                    .getResultList();

            for (Object[] result : results) {
                Order order = new Order();
                order.setOrderId((Integer) result[0]);
                order.setOrderStatus(((Integer) result[1]) != 0); // Преобразуем статус заказа
                Payment payment = new Payment();
                payment.setPaymentSum((Integer) result[2]); // Устанавливаем сумму платежа
                order.setPayment(payment);
                orders.add(order);
            }
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        }

        return orders;
    }
}
