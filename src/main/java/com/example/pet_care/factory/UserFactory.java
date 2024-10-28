package com.example.pet_care.factory;

import com.example.pet_care.entities.User;
import com.example.pet_care.request.RegistrationRequest;

public interface UserFactory {
    public User createUser(RegistrationRequest registrationRequest);
}
