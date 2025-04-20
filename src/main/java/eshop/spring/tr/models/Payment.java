package eshop.spring.tr.models;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Payment {
    private double paymentSum;
    private int bankCardNum;
    private String expiringDate;
}
