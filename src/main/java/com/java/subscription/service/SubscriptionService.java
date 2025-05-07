package com.java.subscription.service;

import com.java.subscription.dto.AvailableServiceResponse;
import com.java.subscription.dto.SubscriptionRequest;
import com.java.subscription.dto.SubscriptionResponse;
import com.java.subscription.dto.TopSubscriptionResponse;
import com.java.subscription.entity.Subscription;
import com.java.subscription.entity.User;
import com.java.subscription.enums.DigitalService;
import com.java.subscription.exception.SubscriptionConflictException;
import com.java.subscription.exception.SubscriptionNotFoundException;
import com.java.subscription.exception.UserNotFoundException;
import com.java.subscription.mapper.ServiceMapper;
import com.java.subscription.mapper.SubscriptionMapper;
import com.java.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final UserService userService;
    private final SubscriptionMapper subscriptionMapper;
    private final ServiceMapper serviceMapper;

    public SubscriptionResponse addSubscription(Long userId, SubscriptionRequest request) {
        log.info("Adding subscription for user ID: {}", userId);

        try {
            User user = userService.getUserById(userId);

            if (subscriptionRepository.existsByUserAndServiceName(user, request.serviceName())) {
                log.warn("Subscription conflict - user {} already has {} subscription",
                        userId, request.serviceName());
                throw new SubscriptionConflictException("Subscription already exists");
            }

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusMonths(1);

            Subscription subscription = subscriptionMapper.toEntity(request);
            subscription.setStartDate(startDate);
            subscription.setEndDate(endDate);
            subscription.setUser(user);

            Subscription savedSubscription = subscriptionRepository.save(subscription);
            log.info("Subscription created - ID: {}, User: {}, Service: {}",
                    savedSubscription.getId(), userId, request.serviceName());

            return subscriptionMapper.toResponse(savedSubscription);

        } catch (UserNotFoundException e) {
            log.error("User not found during subscription creation: {}", userId);
            throw e;
        }
    }

    public List<SubscriptionResponse> getSubscriptionsByUserId(Long userId) {
        log.debug("Fetching subscriptions for user ID: {}", userId);
        return subscriptionRepository.findByUserId(userId).stream()
                .map(subscriptionMapper::toResponse)
                .toList();
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription ID: {}", subscriptionId);

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> {
                    log.error("Subscription not found - ID: {}", subscriptionId);
                    return new SubscriptionNotFoundException(subscriptionId);
                });

        if (!subscription.getUser().getId().equals(userId)) {
            log.warn("Subscription ownership mismatch - User: {}, Subscription owner: {}",
                    userId, subscription.getUser().getId());
            throw new SubscriptionNotFoundException(subscriptionId);
        }

        subscriptionRepository.delete(subscription);
        log.info("Subscription deleted - ID: {}", subscriptionId);
    }

    public List<TopSubscriptionResponse> getTop3Subscriptions() {
        log.debug("Fetching top 3 subscriptions");
        return subscriptionRepository.findTop3Subscriptions().stream()
                .map(subscriptionMapper::toTopResponse)
                .toList();
    }

    public List<AvailableServiceResponse> getAllServices() {
        log.debug("Fetching all available services");
        return Arrays.stream(DigitalService.values())
                .map(serviceMapper::toAvailableServiceResponse)
                .toList();
    }
}
