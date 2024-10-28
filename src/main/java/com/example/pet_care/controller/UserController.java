package com.example.pet_care.controller;

import com.example.pet_care.dto.EntityConverter;
import com.example.pet_care.dto.UserDto;
import com.example.pet_care.entities.User;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.exception.UserAllReadyExistsException;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.request.UserUpdateRequest;
import com.example.pet_care.response.ApiResponse;
import com.example.pet_care.service.user.UserService;
import com.example.pet_care.utils.FeedBackMessage;
import com.example.pet_care.utils.UrlMapping;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(UrlMapping.USERS)
@RestController
public class UserController {

    private final UserService userService;
    private final EntityConverter<User, UserDto> entityConverter;

    @PostMapping(UrlMapping.REGISTER_USER)
    public ResponseEntity<ApiResponse> register(@RequestBody RegistrationRequest registrationRequest)
    {
      try{
          User theUser=userService.register(registrationRequest);
          UserDto registeredUser=entityConverter.mapEntityToDto(theUser, UserDto.class);
          return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS , registeredUser));
      }catch (UserAllReadyExistsException e){
        return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
      }
      catch (Exception e){
          return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
      }

    }

    @PutMapping(UrlMapping.UPDATE_USER)
    public ResponseEntity<ApiResponse> update(@PathVariable Long userId,@RequestBody UserUpdateRequest userUpdateRequest){
        try {
            User theUser=userService.updateUser(userId, userUpdateRequest);
            UserDto updatedUser=entityConverter.mapEntityToDto(theUser, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatedUser));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(UrlMapping.GET_USER_BY_ID)
    public ResponseEntity<ApiResponse> findById(@PathVariable Long userId){
        try {
            User user=userService.findById(userId);
            UserDto theUser=entityConverter.mapEntityToDto(user, UserDto.class);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.FOUND, theUser));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_USER_BY_ID)
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long userId){
        try {
            userService.delete(userId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(UrlMapping.GET_ALL_USERS)
    public ResponseEntity<ApiResponse> getAllUsers(){
        List<UserDto>  theUsers=userService.getAllUsers();
        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS, theUsers));
    }

}
