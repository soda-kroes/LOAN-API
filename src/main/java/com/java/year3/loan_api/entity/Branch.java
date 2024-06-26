package com.java.year3.loan_api.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;
}
