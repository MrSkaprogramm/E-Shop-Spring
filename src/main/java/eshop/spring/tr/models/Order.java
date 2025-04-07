package eshop.spring.tr.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Car\"", schema = "public")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    private Payment payment;
    @Column(name = "brand", nullable = false)
    private boolean orderStatus;
}
