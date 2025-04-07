package eshop.spring.tr.dao.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.PaymentDaoInterface;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentDao implements PaymentDaoInterface {
    private final SessionFactory sessionFactory;

    private static final String UPDATE_ORDER_PAYMENT_INFORMATION =
            "UPDATE payments SET bank_card_num =?, expiring_date=? WHERE orders_id=?;";
    private static final String UPDATE_ORDER_STATUS =
            "UPDATE orders SET order_status = true WHERE id=?;";

    public PaymentDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     *
     * Order payment method
     *
     */
    @Override
    public void makePayment(int orderId, String bankCardNum, String expiringDate)
            throws DaoException {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Начинаем транзакцию

            // Обновление информации о платеже
            Query<?> paymentQuery = session.createNativeQuery(UPDATE_ORDER_PAYMENT_INFORMATION);
            paymentQuery.setParameter(1, bankCardNum);
            paymentQuery.setParameter(2, expiringDate);
            paymentQuery.setParameter(3, orderId);
            paymentQuery.executeUpdate();

            // Обновление статуса заказа
            Query<?> statusQuery = session.createNativeQuery(UPDATE_ORDER_STATUS);
            statusQuery.setParameter(1, orderId);
            statusQuery.executeUpdate();

            transaction.commit(); // Подтверждаем транзакцию

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Откатываем транзакцию в случае ошибки
            }
            throw new DaoException(e.getMessage(), e);
        }
    }
}
