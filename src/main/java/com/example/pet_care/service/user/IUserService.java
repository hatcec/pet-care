package com.example.pet_care.service.user;

import com.example.pet_care.dto.UserDto;
import com.example.pet_care.entities.User;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.request.UserUpdateRequest;

import java.util.List;

public interface IUserService {
    User register(RegistrationRequest registrationRequest);

    User updateUser(Long userId, UserUpdateRequest userUpdateRequest);

    void delete(Long userId);

    User findById(Long userId);

    List<UserDto> getAllUsers();
}
