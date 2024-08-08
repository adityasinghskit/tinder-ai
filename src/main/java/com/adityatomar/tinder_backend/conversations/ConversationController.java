package com.adityatomar.tinder_backend.conversations;

import com.adityatomar.tinder_backend.profiles.Profile;
import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;
    private final ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository, ProfileRepository profileRepository, ConversationService conversationService) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
        this.conversationService = conversationService;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/conversations")
    public Conversation createNewConversation(@RequestBody CreateConversationRequest request){
        profileRepository.findById(request.profileId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile id: " + request.profileId));
        Conversation conversation = new Conversation(
                UUID.randomUUID().toString(),
                request.profileId,
                new ArrayList<>()
        );
        return conversationRepository.save(conversation);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,
                                         @RequestBody ChatMessage chatMessage){
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find cnversation with ID: " + conversationId));
        Profile profile = profileRepository.findById(conversation.profileId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile ID: " + chatMessage.authorId()));
        Profile user = profileRepository.findById(chatMessage.authorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find profile ID: " + chatMessage.authorId()));
        //TODO: Need to validate that author of the message happens to be only the
        // profile associated with the conversation
        ChatMessage newChatMesage = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now());
        conversation.messages().add(newChatMesage);
        conversationService.generateProfileResponse(conversation, profile, user);
        conversationRepository.save(conversation);
        return conversation;

    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/conversations/{conversationId}")
    public Conversation getConversation(@PathVariable String conversationId){
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with ID: " + conversationId));

    }

    public record CreateConversationRequest(
            @NotBlank String profileId
    ){}

}
