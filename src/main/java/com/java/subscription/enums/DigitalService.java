package com.java.subscription.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Arrays;

public enum DigitalService {
    YOUTUBE_PREMIUM("YouTube Premium"),
    VK_MUSIC("VK Музыка"),
    YANDEX_PLUS("Яндекс.Плюс"),
    NETFLIX("Netflix"),
    SPOTIFY("Spotify"),
    APPLE_MUSIC("Apple Music");

    private final String displayName;

    DigitalService(String displayName) {
        this.displayName = displayName;
    }

    @JsonValue
    public String getDisplayName() {
        return displayName;
    }

    @JsonCreator
    public static DigitalService fromDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(e -> e.displayName.equals(displayName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid service: " + displayName));
    }
}