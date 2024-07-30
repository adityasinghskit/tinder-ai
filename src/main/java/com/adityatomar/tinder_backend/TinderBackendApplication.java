package com.adityatomar.tinder_backend;

import com.adityatomar.tinder_backend.profiles.Gender;
import com.adityatomar.tinder_backend.profiles.Profile;
import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class TinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Profile profile = new Profile(
				UUID.randomUUID().toString(),
				"Aditya",
				"Tomar",
				24,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				"foo.png",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
	}
}
