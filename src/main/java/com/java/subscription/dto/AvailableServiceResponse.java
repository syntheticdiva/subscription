package com.java.subscription.dto;

public record AvailableServiceResponse(
        String code,

        String displayName,

        String description
) {}