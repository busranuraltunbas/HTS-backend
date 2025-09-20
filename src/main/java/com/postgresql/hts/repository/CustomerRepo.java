package com.postgresql.hts.repository;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CustomerRepo  extends JpaRepository<Customer, Long> {
    List<Customer> findByIsDeletedFalse(); // aktif kayıtlar
    List<Customer> findByIsDeletedTrue();  // silinmiş kayıtlar
}
