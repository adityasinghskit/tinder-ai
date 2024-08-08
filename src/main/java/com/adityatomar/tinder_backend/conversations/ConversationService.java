package com.adityatomar.tinder_backend.conversations;

import com.adityatomar.tinder_backend.profiles.Profile;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    public ConversationService(OllamaChatClient chatClient) {
        this.chatClient = chatClient;
    }

    private OllamaChatClient chatClient;

    public Conversation generateProfileResponse(Conversation conversation, Profile profile, Profile user){
        SystemMessage systemMessage = new SystemMessage(
                String.format(
                        """
                        You are a %s year old %s %s called %s 
                        %s chatting with a %s year old %s %s on Tinder.
                         This is an in-app text conversation as 
                         if writing on Tinder. Your bio is %s and your Myers Briggs personality
                         type is %s. Keep the responses brief.
                        """, profile.age(), profile.ethnicity(), profile.gender(),
                        profile.firstName(), profile.lastName(), user.age(), user.ethnicity(),
                        user.gender(), profile.bio(), profile.myersBriggsPersonalityType()
                )
        );
        List<AbstractMessage> conversationMessages = conversation.messages().stream().map(message -> {
           if(message.authorId().equals(profile)){
               return new AssistantMessage(message.messageText());
           } else{
               return new UserMessage(message.messageText());
           }
        }).toList();
        List<Message> allMessages = new ArrayList<>();
        allMessages.add(systemMessage);
        allMessages.addAll(conversationMessages);
        Prompt prompt = new Prompt(allMessages);
        ChatResponse response = chatClient.call(prompt);
        System.out.println(response.getResult().getOutput().getContent());
        conversation.messages().add(new ChatMessage(
                response.getResult().getOutput().getContent(),
                profile.id(),
                LocalDateTime.now()));
        return conversation;
    }
}
