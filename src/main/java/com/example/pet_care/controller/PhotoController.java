package com.example.pet_care.controller;

import com.example.pet_care.entities.Photo;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.response.ApiResponse;
import com.example.pet_care.service.photo.PhotoService;
import com.example.pet_care.service.user.UserService;
import com.example.pet_care.utils.FeedBackMessage;
import com.example.pet_care.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.PHOTO)
@RequiredArgsConstructor
public class PhotoController {
    private final PhotoService photoService;
    private final UserService userService;


    @PostMapping(UrlMapping.UPLOAD_PHOTO)
    public ResponseEntity<ApiResponse> uploadPhoto(@RequestParam("file")MultipartFile file, @RequestParam("userId") Long userId){
        try{
            Photo photo=photoService.savePhoto( file, userId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS, photo.getId()));
        } catch (SQLException  |IOException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(FeedBackMessage.SERVER_ERROR, null));
        }

    }

    @PutMapping(UrlMapping.UPDATE_PHOTO)
    public ResponseEntity<ApiResponse> updatePhoto(@PathVariable Long photoId, @RequestBody MultipartFile file) throws SQLException {
        try {
            Photo photo = photoService.getPhotoById(photoId);
            if (photo != null) {
                Photo updatedPhoto = photoService.updatePhoto(photo.getId(), file);
                return ResponseEntity
                        .ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, updatedPhoto.getId()));
            }

        } catch (ResourceNotFoundException | IOException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(null, NOT_FOUND));
        }
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(null, INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping(UrlMapping.DELETE_PHOTO)
    public ResponseEntity<ApiResponse> deletePhoto(@PathVariable long photoId, @PathVariable Long userId){

       try {
           photoService.deletePhoto(photoId, userId);
           return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
       }
       catch (ResourceNotFoundException e){
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(FeedBackMessage.RESOURCE_NOT_FOUND, null));
       }
    }

    @GetMapping(UrlMapping.GET_PHOTO_BY_ID)
    public ResponseEntity<ApiResponse> getPhotoByID(@PathVariable Long photoId){
        try {
            Photo photo = photoService.getPhotoById(photoId);

            if (photo != null) {
                byte[] photoBytes = photoService.getImageData(photo.getId());
                return ResponseEntity
                        .ok(new ApiResponse(FeedBackMessage.RESOURCE_FOUND, photoBytes));
            }

        } catch (ResourceNotFoundException | SQLException e) {
            return ResponseEntity
                    .status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse(null, NOT_FOUND));
    }

    }

