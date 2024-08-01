package com.adityatomar.tinder_backend.matches;

import com.adityatomar.tinder_backend.profiles.Profile;

public record Match(
        String id,
        Profile profile,
        String conversationId
) {
}
