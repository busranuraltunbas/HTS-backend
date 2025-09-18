package com.postgresql.hts.controller;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.model.Customer;
import com.postgresql.hts.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
@CrossOrigin("*")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerRepo.save(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id: " + id));
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Customer updateCustomer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id: " + id));

        updateCustomer.setFirstName(customerDetails.getFirstName());
        updateCustomer.setLastName(customerDetails.getLastName());
        updateCustomer.setPhone_number(customerDetails.getPhone_number());
        updateCustomer.setAddress(customerDetails.getAddress());

        customerRepo.save(updateCustomer);
        return ResponseEntity.ok(updateCustomer);
    }

    /*@DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCustomer(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id: " + id));
        customerRepo.delete(customer);
        customer.setDeleted(true);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    // ✅ Soft delete
    @DeleteMapping("/{id}")
    public String softDeleteAnimal(@PathVariable Long id) {
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setDeleted(true); // Soft delete işareti
        customerRepo.save(customer);

        return "Customer with id " + id + " soft deleted.";
    }
}