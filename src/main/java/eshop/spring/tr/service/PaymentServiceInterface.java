package eshop.spring.tr.service;

public interface PaymentServiceInterface {
    public void makePayment(int orderId, String bankCardNum, String expiringDate)
            throws ServiceException;
}
