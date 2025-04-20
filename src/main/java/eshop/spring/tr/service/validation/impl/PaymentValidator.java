package eshop.spring.tr.service.validation.impl;

import eshop.spring.tr.service.validation.PaymentValidatorInterface;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator implements PaymentValidatorInterface {
    private static final String BANK_CARD_NUM_REGEX = "[0-9]{16}";
    private static final String EXPIRING_DATE_REGEX = "[0-9]{4}";

    public boolean checkPaymentInfo(String bankCardNum, String expiringDate) {
        boolean isValid = true;

        if (!validatePaymentBankCardNum(bankCardNum)) {
            isValid = false;
        }

        if (!validatePaymentExpiringDate(expiringDate)) {
            isValid = false;
        }
        return isValid;
    }

    @Override
    public boolean validatePaymentBankCardNum(String bankCardNum) {
        return bankCardNum.matches(BANK_CARD_NUM_REGEX);
    }

    @Override
    public boolean validatePaymentExpiringDate(String expiringDate) {
        return expiringDate.matches(EXPIRING_DATE_REGEX);
    };
}
