package com.java.subscription.service;

import com.java.subscription.dto.UserRequest;
import com.java.subscription.entity.User;
import com.java.subscription.exception.UserNotFoundException;
import com.java.subscription.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(UserRequest request) {
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public User updateUser(Long id, UserRequest request) {
        User user = getUserById(id);
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}

