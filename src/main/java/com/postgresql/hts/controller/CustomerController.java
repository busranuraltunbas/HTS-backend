package com.postgresql.hts.controller;

import com.postgresql.hts.model.Customer;
import com.postgresql.hts.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
public class CustomerController{

    @Autowired
    CustomerRepo customerRepo;

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

    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomerById(@PathVariable(value = "id") Long id){
        return customerRepo.findById(id);
    }

}
