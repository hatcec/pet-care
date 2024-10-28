package com.example.pet_care.service.user;

import com.example.pet_care.entities.User;
import com.example.pet_care.factory.UserFactory;
import com.example.pet_care.repository.UserRepository;
import com.example.pet_care.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    public User add(RegistrationRequest registrationRequest) {

       return userFactory.createUser(registrationRequest);
    }
}
