package com.adityatomar.tinder_backend;

import com.adityatomar.tinder_backend.conversations.ChatMessage;
import com.adityatomar.tinder_backend.conversations.Conversation;
import com.adityatomar.tinder_backend.conversations.ConversationRepository;
import com.adityatomar.tinder_backend.profiles.Gender;
import com.adityatomar.tinder_backend.profiles.Profile;
import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		profileRepository.deleteAll();
		conversationRepository.deleteAll();

		Profile profile = new Profile(
				"1",
				"Aditya",
				"Tomar",
				24,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				"foo.png",
				"INTP"
		);

		Profile profile2 = new Profile(
				"2",
				"Palki",
				"Singh",
				29,
				"Indian",
				Gender.MALE,
				"Software Programmer",
				"foo.png",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.save(profile2);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				UUID.randomUUID().toString(),
				profile.id(),
				List.of(
						new ChatMessage("Hi", profile.id(), LocalDateTime.now())
				)
		);

		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
