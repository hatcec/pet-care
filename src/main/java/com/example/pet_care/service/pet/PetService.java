package com.example.pet_care.service.pet;

import com.example.pet_care.entities.Pet;

import java.util.List;

public interface PetService {
    List<Pet> savePetForAppointment(List<Pet> pets);
    Pet updatePet(Long id, Pet pet);
    void deletePet(Long id);
    Pet getPetById(Long id);

}
