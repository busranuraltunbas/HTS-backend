package com.postgresql.hts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "customer")
@SequenceGenerator(name = "CUSTOMER_SEQ", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
public class Customer extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CUSTOMER_SEQ")
    private long id;

    @NotBlank(message = "Ad boş bırakılamaz.")
    @Size(max = 50, message = "Ad en fazla 50 karakter olabilir.")
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotBlank(message = "Soyad boş bırakılamaz.")
    @Size(max = 50, message = "Soyad en fazla 50 karakter olabilir.")
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotBlank(message = "Telefon numarası boş bırakılamaz.")
    @Pattern(
            regexp = "^\\+?[0-9]{10,15}$",
            message = "Telefon numarası 10-15 rakamdan oluşmalı; başında isteğe bağlı + olabilir."
    )
    @Column(name = "PHONE_NUM")
    private String phone_number;

    @NotBlank(message = "Adres boş bırakılamaz.")
    @Size(max = 250, message = "Adres en fazla 250 karakter olabilir.")
    @Column(name = "ADDRESS")
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id", referencedColumnName = "id")
    private Animal animal;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Animal> animals = new ArrayList<>();
}
