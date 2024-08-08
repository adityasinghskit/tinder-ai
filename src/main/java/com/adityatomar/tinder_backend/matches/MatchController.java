package com.adityatomar.tinder_backend.matches;

import com.adityatomar.tinder_backend.conversations.Conversation;
import com.adityatomar.tinder_backend.conversations.ConversationRepository;
import com.adityatomar.tinder_backend.profiles.Profile;
import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class MatchController {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private MatchRepository matchRepository;

    @CrossOrigin(origins = "*")
    @PostMapping("/matches")
    public Match createNewMatch(@RequestBody CreateMatchRequest request){
        Profile profile = profileRepository.findById(request.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile id: " + request.profileId));
        //TODO: Make sure there no existing convo with this profile already
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId,
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        Match match = new Match(
                UUID.randomUUID().toString(),
                profile,
                conversation.id()
        );
        return  matchRepository.save(match);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/matches")
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    public record CreateMatchRequest(
            String profileId
    ){}

}
