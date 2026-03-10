package com.postgresql.hts.controller;

import com.postgresql.hts.model.UserEntity;
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
    public void addUser(@RequestBody UserEntity userEntity){

        userEntity.setCreatedDate(new Date());
        repo.save(userEntity);
        //user.setCreatedUser(Context.getloginuserName);
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUsers(){
        return repo.findAll();
    }

    @GetMapping("user/{id}")
    public Optional<UserEntity> getUserById(@PathVariable(value = "id") Long id){
        return repo.findById(id);
    }

    /*@DeleteMapping("/deleteUserById/{id}")
    public void deleteUserById(@PathVariable(value = "id") Long id){
        repo.deleteById(id);
    }*/





}

