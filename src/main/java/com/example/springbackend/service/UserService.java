package com.example.springbackend.service;

import com.example.springbackend.dto.UserRequest;
import com.example.springbackend.model.Role;
import com.example.springbackend.model.User;
import com.example.springbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // In a real application, you should hash the password
        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}