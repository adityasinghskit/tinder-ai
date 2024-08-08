package com.adityatomar.tinder_backend;

import com.adityatomar.tinder_backend.conversations.ChatMessage;
import com.adityatomar.tinder_backend.conversations.Conversation;
import com.adityatomar.tinder_backend.conversations.ConversationRepository;
import com.adityatomar.tinder_backend.matches.Match;
import com.adityatomar.tinder_backend.matches.MatchRepository;
import com.adityatomar.tinder_backend.profiles.Gender;
import com.adityatomar.tinder_backend.profiles.Profile;
import com.adityatomar.tinder_backend.profiles.ProfileService;
import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class TinderBackendApplication implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private ProfileService profileService;

	public static void main(String[] args) {
		SpringApplication.run(TinderBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		profileRepository.deleteAll();
		conversationRepository.deleteAll();
		matchRepository.deleteAll();

		profileService.loadProfilesFromJson();
		Profile profile =  profileService.findById("user");
		Profile profile2 = profileService.findById("2");
		Profile profile3 = profileService.findById("8");
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation = new Conversation(
				UUID.randomUUID().toString(),
				profile2.id(),
				List.of(
						new ChatMessage("Hi", profile.id(), LocalDateTime.now())
				)
		);
		Match match = new Match(
				UUID.randomUUID().toString(),
				profile2,
				conversation.id()
		);
		Conversation conversation2 = new Conversation(
				UUID.randomUUID().toString(),
				profile3.id(),
				List.of(
						new ChatMessage("Hi", profile.id(), LocalDateTime.now())
				)
		);
		Match match2 = new Match(
				UUID.randomUUID().toString(),
				profile3,
				conversation2.id()
		);
		matchRepository.save(match);
		matchRepository.save(match2);
		conversationRepository.save(conversation);
		conversationRepository.save(conversation2);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
