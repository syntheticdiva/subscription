package com.java.subscription.service;

import com.java.subscription.dto.AvailableServiceResponse;
import com.java.subscription.dto.SubscriptionRequest;
import com.java.subscription.dto.SubscriptionResponse;
import com.java.subscription.dto.TopSubscriptionResponse;
import com.java.subscription.entity.Subscription;
import com.java.subscription.entity.User;
import com.java.subscription.enums.DigitalService;
import com.java.subscription.exception.SubscriptionNotFoundException;
import com.java.subscription.mapper.ServiceMapper;
import com.java.subscription.mapper.SubscriptionMapper;
import com.java.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final SubscriptionMapper subscriptionMapper;
    private final ServiceMapper serviceMapper;

    public SubscriptionResponse addSubscription(Long userId, SubscriptionRequest request) {
        User user = userService.getUserById(userId);
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusMonths(1);
        Subscription subscription = subscriptionMapper.toEntity(request);
        subscription.setStartDate(startDate);
        subscription.setEndDate(endDate);
        subscription.setUser(user);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toResponse(savedSubscription);
    }

    public List<SubscriptionResponse> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toResponse)
                .toList();
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));

        if (!subscription.getUser().getId().equals(userId)) {
            throw new SubscriptionNotFoundException(subscriptionId);
        }

        subscriptionRepository.delete(subscription);
    }

    public List<TopSubscriptionResponse> getTop3Subscriptions() {
        return subscriptionRepository.findTop3Subscriptions().stream()
                .map(subscriptionMapper::toTopResponse)
                .toList();
    }
    public List<AvailableServiceResponse> getAllServices() {
        return Arrays.stream(DigitalService.values())
                .map(serviceMapper::toAvailableServiceResponse)
                .toList();
    }
}
