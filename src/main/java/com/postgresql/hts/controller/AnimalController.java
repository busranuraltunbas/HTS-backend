package com.postgresql.hts.controller;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.model.Customer;
import com.postgresql.hts.model.BaseEntity;
import com.postgresql.hts.repository.AnimalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@CrossOrigin("*")
public class AnimalController {
    @Autowired
    private  AnimalRepo animalRepo;

    // build create animal REST API
    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal){
        //animal.setState(true);
        //animal.setCreatedDate(new Date());
        return animalRepo.save(animal);
    }

    @GetMapping
    public List<Animal> getAllAnimals(){
        return animalRepo.findByIsDeletedFalse();
    }

    //build get animal by id REST API
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(@PathVariable Long id){
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not exist with id" + id));
        return ResponseEntity.ok(animal);
    }

    //build update animal REST API
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(@PathVariable Long id, @RequestBody Animal animalDetails){
        Animal updateAnimal =  animalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not exist with id" + id));
        updateAnimal.setCutNumber(animalDetails.getCutNumber());
        updateAnimal.setAge(animalDetails.getAge());
        updateAnimal.setSalesNumber(animalDetails.getSalesNumber());
        updateAnimal.setEarningNumber(animalDetails.getEarningNumber());
        updateAnimal.setPrice(animalDetails.getPrice());

        updateAnimal.setType(animalDetails.getType());
        updateAnimal.setWeight(animalDetails.getWeight());
        updateAnimal.setShare(animalDetails.getShare());
        updateAnimal.setIsSale(animalDetails.getIsSale()); // boolean field

        animalRepo.save(updateAnimal);
        return ResponseEntity.ok(updateAnimal);
    }

    //build delete animal REST API
    /*@DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAnimal(@PathVariable Long id){
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Animal not exist with id" + id));
        animalRepo.delete(animal);
        animal.setDeleted(true);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    // ✅ Soft delete
    @DeleteMapping("/{id}")
    public String softDeleteAnimal(@PathVariable Long id) {
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal not found"));

        animal.setDeleted(true); // Soft delete işareti
        animalRepo.save(animal);

        return "Animal with id " + id + " soft deleted.";
    }

    // Silinmiş hayvanlar
    @GetMapping("/deleted")
    public List<Animal> getDeletedAnimals() {
        return animalRepo.findByIsDeletedTrue();
    }

}
