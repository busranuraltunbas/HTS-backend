package com.postgresql.hts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "STATE")
    private boolean state;

    @Column(name="CREATED_USER")
    private String createdUser;

    @Column(name="UPDATED_USER")
    private String updatedUser;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="UPDATED_DATE")
    private Date updatedDate;
}
