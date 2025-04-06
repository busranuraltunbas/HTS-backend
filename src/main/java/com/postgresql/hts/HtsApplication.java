package com.postgresql.hts;

import com.postgresql.hts.model.Animal;
import com.postgresql.hts.model.User;
import com.postgresql.hts.repository.AnimalRepo;
import com.postgresql.hts.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtsApplication implements CommandLineRunner {

    @Autowired
    private AnimalRepo animalRepo;

	public static void main(String[] args) {
		SpringApplication.run(HtsApplication.class, args);
	}

	@Autowired
	UserRepo userRepo;

	@Override
	public void run(String... args) throws Exception {

//		User user = new User();
//		user.setFirstName("Adem");
//		user.setLastName("KORKUT");
//		user.setPhoneNum("111111111");
//		userRepo.save(user);


		/*Animal animal = new Animal();
		animal.setEarningNumber("5");
		animal.setAge("6");
		animal.setCutNumber("9");
		animalRepo.save(animal);*/

	}
}
