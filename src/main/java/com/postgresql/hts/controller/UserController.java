package com.postgresql.hts.controller;

import com.postgresql.hts.model.User;
import com.postgresql.hts.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserRepo repo;

    @PostMapping("/addUser")
    public void addUser(@RequestBody User user){
        user.setState(true);
        user.setCreatedDate(new Date());
        repo.save(user);
        //user.setCreatedUser(Context.getloginuserName);
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return repo.findAll();
    }

    @GetMapping("user/{id}")
    public Optional<User> getUserById(@PathVariable(value = "id") Long id){
        return repo.findById(id);
    }

    /*@DeleteMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable(value = "id") Long id){
        repo.deleteById(id);
    }*/





}

