package com.postgresql.hts;

import com.postgresql.hts.model.User;
import com.postgresql.hts.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HtsApplication implements CommandLineRunner {

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

	}
}
