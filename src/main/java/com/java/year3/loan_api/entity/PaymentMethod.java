package com.java.year3.loan_api.entity;

import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "payment_method")
@Data
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "code")
    private String code;
}
