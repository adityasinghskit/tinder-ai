package com.adityatomar.tinder_backend.profiles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Value("${tinderai.character.user}")
    private String userJson;

    public void loadProfilesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ClassPathResource resource = new ClassPathResource("profiles.json");
            List<Profile> profiles = objectMapper.readValue(resource.getInputStream(), new TypeReference<List<Profile>>() {});
            Profile userProfile = objectMapper.readValue(userJson, Profile.class);
            profiles.add(userProfile);
            profileRepository.saveAll(profiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Profile findById(String id){
        return profileRepository.findById(id).orElseThrow(()-> new RuntimeException("Profile not found with id: " + id));
    }
}
