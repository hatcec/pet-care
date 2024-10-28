package com.example.pet_care.controller;

import com.example.pet_care.dto.EntityConverter;
import com.example.pet_care.dto.UserDto;
import com.example.pet_care.entities.User;
import com.example.pet_care.exception.UserAllReadyExistsException;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.response.ApiResponse;
import com.example.pet_care.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;

    @PostMapping
    public ResponseEntity<ApiResponse> add(@RequestBody RegistrationRequest registrationRequest)
    {
      try{
          User theUser=userService.add(registrationRequest);
          UserDto registeredUser=entityConverter.mapEntityToDto(theUser, UserDto.class);
          return ResponseEntity.ok(new ApiResponse("USer registered successfully " , registeredUser));
      }catch (UserAllReadyExistsException e){
        return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
      }

    }
}
