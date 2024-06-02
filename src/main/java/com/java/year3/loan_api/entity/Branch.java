package com.java.year3.loan_api.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "branch_name", nullable = false, length = 255)
    private String branchName;

    @Column(name = "branch_code", nullable = false, length = 100)
    private String branchCode;
}
