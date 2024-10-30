package com.example.pet_care.controller;

import com.example.pet_care.entities.Pet;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.response.ApiResponse;
import com.example.pet_care.service.pet.PetService;
import com.example.pet_care.utils.FeedBackMessage;
import com.example.pet_care.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(UrlMapping.PETS)
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping(UrlMapping.SAVE_PETS_APPOINTMENT)
    public ResponseEntity<ApiResponse> savePets(@RequestBody List<Pet> pets){
        try {
            List<Pet> savedPets = petService.savePetForAppointment(pets);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS, savedPets));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(UrlMapping.GET_PET_BY_ID)
    public ResponseEntity<ApiResponse> getPetById(@PathVariable Long id){
        try{
            Pet pet=petService.getPetById(id);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.FOUND, pet));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_PET_BY_ID)
    public ResponseEntity<ApiResponse> deletePetId(@PathVariable Long id){
        try{
           petService.getPetById(id);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS,null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_PET)
    public ResponseEntity<ApiResponse> updatePet(@PathVariable Long id, @RequestBody Pet pet){
        try {
            Pet thePet=petService.updatePet(id, pet);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, thePet));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
}
