package com.postgresql.hts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
@SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)

public class Customer  extends BaseEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CUSTOMER_SEQ")
    private long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE_NUM")
    private String phone_number;

    @Column(name = "ADDRESS")
    private String address;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Animal> animals = new ArrayList<>();












}
