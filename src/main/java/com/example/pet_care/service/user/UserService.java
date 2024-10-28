package com.example.pet_care.service.user;

import com.example.pet_care.dto.EntityConverter;
import com.example.pet_care.dto.UserDto;
import com.example.pet_care.entities.User;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.factory.UserFactory;
import com.example.pet_care.repository.UserRepository;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final EntityConverter<User, UserDto> entityConverter;
    @Override
    public User register(RegistrationRequest registrationRequest) {

       return userFactory.createUser(registrationRequest);
    }

    @Override
    public User updateUser(Long userId, UserUpdateRequest userUpdateRequest){
        User user=findById(userId);
        user.setFirstName(userUpdateRequest.getFirstName());
        user.setLastName(userUpdateRequest.getLastName());
        user.setGender(userUpdateRequest.getGender());
        user.setPassword(userUpdateRequest.getPhoneNumber());
        user.setPhoneNumber(userUpdateRequest.getPhoneNumber());
        user.setSpecialization(userUpdateRequest.getSpecialization());


        return userRepository.save(user);
    }
    @Override
    public void delete(Long userId){
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, ()->{
            throw new ResourceNotFoundException("User not found");
        });
    }
    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("USer not found"));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(user -> entityConverter.mapEntityToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

}
