package eshop.spring.tr.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private double paymentSum;
    private int bankCardNum;
    private String expiringDate;
}
