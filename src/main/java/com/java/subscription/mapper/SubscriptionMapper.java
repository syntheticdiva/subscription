package com.java.subscription.mapper;

import com.java.subscription.dto.SubscriptionRequest;
import com.java.subscription.dto.SubscriptionResponse;
import com.java.subscription.dto.TopSubscriptionResponse;
import com.java.subscription.entity.Subscription;
import com.java.subscription.enums.DigitalService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(target = "userId", source = "user.id")
    SubscriptionResponse toResponse(Subscription subscription);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    Subscription toEntity(SubscriptionRequest request);

default TopSubscriptionResponse toTopResponse(Object[] tuple) {
    return new TopSubscriptionResponse(
            (String) tuple[0],
            ((Number) tuple[1]).longValue()
    );
}
}

