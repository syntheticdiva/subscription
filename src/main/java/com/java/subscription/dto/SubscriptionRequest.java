package com.java.subscription.dto;

import com.java.subscription.enums.DigitalService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequest(
        @Schema(
                example = "YouTube Premium, VK Музыка, Яндекс.Плюс, Netflix, Spotify, Apple Music"
        )
        @NotNull DigitalService serviceName

) {}