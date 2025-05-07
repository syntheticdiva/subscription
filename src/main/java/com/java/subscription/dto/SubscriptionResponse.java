package com.java.subscription.dto;

import java.time.LocalDate;

public record SubscriptionResponse(
        Long id,
        String serviceName,
        LocalDate startDate,
        LocalDate endDate,
        Long userId
) {}
