package com.postgresql.hts.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "us_user")
@SequenceGenerator(name = "USER_SEQ",sequenceName = "USER_SEQ", allocationSize = 1)
public class User extends BaseEntity {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "USER_SEQ")
    private long id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="PHONE_NUM")
    private String phoneNum;




}
