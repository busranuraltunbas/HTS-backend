package com.postgresql.hts.controller;

import com.postgresql.hts.model.Customer;
import com.postgresql.hts.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class CustomerController{

    @Autowired
    CustomerRepo customerRepo;

    //eski
    @PostMapping("/addCustomer")
    public void addCustomer(@RequestBody Customer customer){
        customer.setState(true);
        customer.setCreatedDate(new Date());
        customerRepo.save(customer);
    }

    // build create customer REST API
    @PostMapping("/createCustomer")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepo.save(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerRepo.findAll();
    }

    //build get customer by id REST API
    @GetMapping("/customer/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id" + id));
        return ResponseEntity.ok(customer);
    }

    //build update customer REST API
    @PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails){
        Customer updateCustomer =  customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id" + id));
        updateCustomer.setFirstName(customerDetails.getFirstName());
        updateCustomer.setLastName(customerDetails.getLastName());
        updateCustomer.setPhone_number(customerDetails.getPhone_number());
        updateCustomer.setAddress(customerDetails.getAddress());

        customerRepo.save(updateCustomer);
        return ResponseEntity.ok(updateCustomer);
    }

}
