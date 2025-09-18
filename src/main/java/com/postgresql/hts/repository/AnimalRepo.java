package com.postgresql.hts.repository;

import com.postgresql.hts.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AnimalRepo extends JpaRepository<Animal, Long> {
    List<Animal> findByIsDeletedFalse(); // aktif kayıtlar
    List<Animal> findByIsDeletedTrue();  // silinmiş kayıtlar
}
