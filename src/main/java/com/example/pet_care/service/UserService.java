package com.example.pet_care.service;

import com.example.pet_care.entities.User;
import com.example.pet_care.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public void add(User user) {
    userRepository.save(user);
    }
}
