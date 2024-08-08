package com.adityatomar.tinder_backend.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileService profileService;

    @CrossOrigin(origins = "*")
    @GetMapping("/profiles/random")
    public Profile getRandomProfile(){
        return profileService.getRandomProfile();
    }
}
