package com.java.subscription.service;

import com.java.subscription.dto.UserRequest;
import com.java.subscription.entity.User;
import com.java.subscription.exception.UserAlreadyExistsException;
import com.java.subscription.exception.UserNotFoundException;
import com.java.subscription.exception.UserValidationException;
import com.java.subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserRequest request) {
        log.info("Attempting to create user with email: {}", request.email());

        validateUserRequest(request);

        if (userRepository.existsByEmail(request.email())) {
            log.warn("User creation failed - email already exists: {}", request.email());
            throw new UserAlreadyExistsException(request.email());
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());

        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());

        return savedUser;
    }

    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new UserNotFoundException(id);
                });
    }

    @Transactional
    public User updateUser(Long id, UserRequest request) {
        log.info("Attempting to update user with ID: {}", id);

        validateUserRequest(request);

        User user = getUserById(id);

        if (!user.getEmail().equals(request.email()) &&
                userRepository.existsByEmail(request.email())) {
            log.warn("User update failed - new email already exists: {}", request.email());
            throw new UserAlreadyExistsException(request.email());
        }

        user.setName(request.name());
        user.setEmail(request.email());

        User updatedUser = userRepository.save(user);
        log.info("User with ID {} updated successfully", id);

        return updatedUser;
    }

    @Transactional
    public void deleteUser(Long id) {
        log.info("Attempting to delete user with ID: {}", id);

        if (!userRepository.existsById(id)) {
            log.error("Delete failed - user not found with ID: {}", id);
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);
        log.info("User with ID {} deleted successfully", id);
    }

    private void validateUserRequest(UserRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (Objects.isNull(request.name()) || request.name().isBlank()) {
            log.warn("Validation failed - user name cannot be empty");
            errors.put("name", "Name cannot be empty");
        }

        if (Objects.isNull(request.email())) {
            log.warn("Validation failed - user email cannot be null");
            errors.put("email", "Email required field");
        } else if (!request.email().contains("@")) {
            log.warn("Validation failed - invalid email format: {}", request.email());
            errors.put("email", "Incorrect email format");
        }

        if (!errors.isEmpty()) {
            throw new UserValidationException(errors);
        }
    }
}

