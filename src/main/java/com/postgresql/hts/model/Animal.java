package com.postgresql.hts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "animal")
@SequenceGenerator(name = "ANIMAL_SEQ", sequenceName = "ANIMAL_SEQ", allocationSize = 1)
public class Animal extends BaseEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ANIMAL_SEQ")
    private Long id;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "EARNING_NUMBER")
    private String earningNumber;

    @Column(name = "SALES_NUMBER")
    private String salesNumber;

    @Column(name = "CUT_NUMBER")
    private String cutNumber;

    @Column(name = "AGE")
    private String age;

    @Column(name = "IS_SALE")
    private Boolean isSale;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "WEIGHT")
    private BigDecimal weight;

    @Column(name = "SHARE")
    private int share;

    @OneToMany(mappedBy = "animal", cascade = CascadeType.ALL)
    private List<Customer> customers = new ArrayList<>();


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;








}
