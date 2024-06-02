package com.java.year3.loan_api.entity;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "parent")
    private String parent;

    @Column(name = "code")
    private String code;

    @Column(name = "name_latin")
    private String nameLatin;

    @Column(name = "name_khmer")
    private String nameKhmer;
}
