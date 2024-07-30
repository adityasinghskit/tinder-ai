package com.adityatomar.tinder_backend.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String messgeText,
        String authorId,
        LocalDateTime messageTime
) {
}
