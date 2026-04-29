package com.postgresql.hts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.sql.Timestamp;

@Data
@Entity
@Table(name = "us_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //?
    private Long id;

    @Column(name="USER_ID", unique = true)
    private String userId;

    @Column(name="USER_NAME")
    private String userName;

    @Column(name="EMAIL",unique = true)
    private String email;

    @Column(name="PASSWORD")
    private String password;

    @Column(name = "VERIFY_OTP")
    private String verifyOtp;

    @Column(name = "IS_ACCOUNT_VERYFIED")
    private Boolean isAccountVerified;

    @Column(name = "VERIFY_OTP_EXPIRED_AT")
    private Long verifyOtpExpireAt;

    @Column(name = "RESET_OTP")
    private String resetOtp;


    @Column(name = "RESET_OTP_EXPIRED_AT")
    private Long resetOtpExpireAt;

    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private Timestamp createdAt;

    @CreationTimestamp
    @Column(name = "UPDATED_AT")
    private Timestamp updatedAt;


    @Enumerated(EnumType.STRING)
    private Role role;




}
