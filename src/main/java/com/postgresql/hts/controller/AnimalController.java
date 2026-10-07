package com.postgresql.hts.controller;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.repository.AnimalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalRepo animalRepo;

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalRepo.save(animal);
    }

    // USER + ADMIN
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalRepo.findByIsDeletedFalse();
    }

    // SADECE ADMIN
    @GetMapping("/deleted")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Animal> getDeletedAnimals() {
        return animalRepo.findByIsDeletedTrue();
    }

    // USER + ADMIN
    @GetMapping("/{id}")
    public ResponseEntity<Animal> getAnimalById(
            @PathVariable Long id
    ) {
        Animal animal = animalRepo.findById(id)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal not found with id " + id
                        )
                );

        return ResponseEntity.ok(animal);
    }

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Animal> updateAnimal(
            @PathVariable Long id,
            @RequestBody Animal animalDetails
    ) {
        Animal updateAnimal = animalRepo.findById(id)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal not found with id " + id
                        )
                );

        updateAnimal.setCutNumber(animalDetails.getCutNumber());
        updateAnimal.setAge(animalDetails.getAge());
        updateAnimal.setSalesNumber(animalDetails.getSalesNumber());
        updateAnimal.setEarningNumber(animalDetails.getEarningNumber());
        updateAnimal.setPrice(animalDetails.getPrice());
        updateAnimal.setType(animalDetails.getType());
        updateAnimal.setWeight(animalDetails.getWeight());
        updateAnimal.setShare(animalDetails.getShare());
        updateAnimal.setIsSale(animalDetails.getIsSale());

        animalRepo.save(updateAnimal);

        return ResponseEntity.ok(updateAnimal);
    }

    // SADECE ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> softDeleteAnimal(
            @PathVariable Long id
    ) {
        Animal animal = animalRepo.findById(id)
                .filter(a -> !a.isDeleted())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Animal not found with id " + id
                        )
                );

        animal.setDeleted(true);
        animalRepo.save(animal);

        return ResponseEntity.ok(
                "Animal with id " + id + " soft deleted."
        );
    }
}