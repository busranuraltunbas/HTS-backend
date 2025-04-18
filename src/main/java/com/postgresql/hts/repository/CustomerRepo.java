package com.postgresql.hts.repository;

import com.postgresql.hts.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CustomerRepo  extends JpaRepository<Customer, Long> {
}
