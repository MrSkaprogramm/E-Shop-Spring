package eshop.spring.tr.service.impl;

import eshop.spring.tr.dao.DaoException;
import eshop.spring.tr.dao.impl.PaymentDao;
import eshop.spring.tr.service.PaymentServiceInterface;
import eshop.spring.tr.service.ServiceException;
import eshop.spring.tr.service.validation.impl.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService implements PaymentServiceInterface {
    private final PaymentDao paymentDao;
    private final PaymentValidator paymentValidator;

    @Autowired
    public PaymentService(PaymentDao paymentDao, PaymentValidator paymentValidator) {
        this.paymentDao = paymentDao;
        this.paymentValidator = paymentValidator;
    }

    /**
     *
     * Order payment method
     *
     */
    @Override
    public void makePayment(int orderId, String bankCardNum, String expiringDate)
            throws ServiceException {

        if (!paymentValidator.checkPaymentInfo(bankCardNum, expiringDate)) {
            throw new ServiceException("Wrong payment data");
        }

        try {
            paymentDao.makePayment(orderId, bankCardNum, expiringDate);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
