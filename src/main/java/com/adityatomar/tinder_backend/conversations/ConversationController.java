package com.adityatomar.tinder_backend.conversations;

import com.adityatomar.tinder_backend.profiles.ProfileRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
public class ConversationController {

    private final ConversationRepository conversationRepository;
    private final ProfileRepository profileRepository;

    public ConversationController(ConversationRepository conversationRepository,
                                  ProfileRepository profileRepository) {
        this.conversationRepository = conversationRepository;
        this.profileRepository = profileRepository;
    }

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

    @PostMapping(value = "/conversations/{conversationId}")
    public Conversation addMessageToConversation(@PathVariable String conversationId,
                                         @RequestBody ChatMessage chatMessage){
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find cnversation with ID: " + conversationId));
        profileRepository.findById(chatMessage.authorId())
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
        conversationRepository.save(conversation);
        return conversation;

    }

    public record CreateConversationRequest(
            @NotBlank String profileId
    ){}

}
