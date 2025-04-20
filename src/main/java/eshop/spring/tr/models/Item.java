package eshop.spring.tr.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"Car\"", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "itemInfo", nullable = false)
    private String itemInfo;
    @Column(name = "price", nullable = false)
    private int price;
}
