package com.postgresql.hts.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "IS_DELETED")
    private boolean isDeleted = false;

    @Column(name="CREATED_USER")
    private String createdUser;

    @Column(name="UPDATED_USER")
    private String updatedUser;

    @Column(name="CREATED_DATE")
    private Date createdDate;

    @Column(name="UPDATED_DATE")
    private Date updatedDate;
}
