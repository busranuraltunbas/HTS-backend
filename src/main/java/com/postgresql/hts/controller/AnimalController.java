package com.postgresql.hts.controller;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.repository.AnimalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class AnimalController {
    @Autowired
    AnimalRepo animalRepo;

    @PostMapping("/addAnimal")
    public void addAnimal(@RequestBody Animal animal){
        animal.setState(true);
        animal.setCreatedDate(new Date());
        animalRepo.save(animal);
    }

    @GetMapping("/animals")
    public List<Animal> getAllAnimals(){
        return animalRepo.findAll();
    }

    @GetMapping("animal/{id}")
    public Optional<Animal> getAnimalById(@PathVariable(value = "id") Long id){
        return animalRepo.findById(id);
    }
}
