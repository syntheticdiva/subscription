package com.java.subscription.repository;

import com.java.subscription.entity.Subscription;
import com.java.subscription.entity.User;
import com.java.subscription.enums.DigitalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    boolean existsByUserAndServiceName(User user, DigitalService serviceName);

    @Query(value = "SELECT s.service_name as serviceName," +
            " COUNT(s.id) as count FROM subscriptions s GROUP BY s.service_name " +
            "ORDER BY count DESC LIMIT 3", nativeQuery = true)
    List<Object[]> findTop3Subscriptions();

    List<Subscription> findByUserId(Long userId);
}
