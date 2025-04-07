package eshop.spring.tr.dao;

public interface PaymentDaoInterface {
    public void makePayment(int orderId, String bankCardNum, String expiringDate) throws DaoException;
}
