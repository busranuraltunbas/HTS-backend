package com.postgresql.hts.repository;

import com.postgresql.hts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    // Aktif müşteriler
    List<Customer> findByIsDeletedFalse();

    // Soft delete ile silinmiş müşteriler
    List<Customer> findByIsDeletedTrue();
}