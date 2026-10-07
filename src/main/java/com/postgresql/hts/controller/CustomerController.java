package com.postgresql.hts.controller;

import com.postgresql.hts.model.Customer;
import com.postgresql.hts.repository.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerRepo customerRepo;

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    // USER + ADMIN
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepo.findByIsDeletedFalse();
    }

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleted")
    public List<Customer> getDeletedCustomers() {
        return customerRepo.findByIsDeletedTrue();
    }

    // USER + ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @PathVariable Long id
    ) {
        Customer customer = customerRepo.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id " + id
                        )
                );

        return ResponseEntity.ok(customer);
    }

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer customerDetails
    ) {
        Customer updateCustomer = customerRepo.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id " + id
                        )
                );

        updateCustomer.setFirstName(customerDetails.getFirstName());
        updateCustomer.setLastName(customerDetails.getLastName());
        updateCustomer.setPhone_number(customerDetails.getPhone_number());
        updateCustomer.setAddress(customerDetails.getAddress());

        customerRepo.save(updateCustomer);

        return ResponseEntity.ok(updateCustomer);
    }

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteCustomer(
            @PathVariable Long id
    ) {
        Customer customer = customerRepo.findById(id)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found with id " + id
                        )
                );

        customer.setDeleted(true);
        customerRepo.save(customer);

        return ResponseEntity.ok(
                "Customer with id " + id + " soft deleted."
        );
    }
}