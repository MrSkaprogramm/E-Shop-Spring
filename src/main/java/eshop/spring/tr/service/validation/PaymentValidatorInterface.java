package eshop.spring.tr.service.validation;

public interface PaymentValidatorInterface {
    public boolean checkPaymentInfo(String bankCardNum, String expiringDate);

    public boolean validatePaymentBankCardNum(String bankCardNum);

    public boolean validatePaymentExpiringDate(String expiringDate);
}
