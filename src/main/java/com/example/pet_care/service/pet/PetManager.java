package com.example.pet_care.service.pet;

import com.example.pet_care.entities.Pet;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.repository.PetRepository;
import com.example.pet_care.utils.FeedBackMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PetManager implements PetService{
    private final PetRepository petRepository;

    @Override
    public List<Pet> savePetForAppointment(List<Pet> pets) {
        return petRepository.saveAll(pets);
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        Pet existingPet=getPetById(id);
        existingPet.setName(pet.getName());
        existingPet.setAge(pet.getAge());
        existingPet.setColor(pet.getColor());
        existingPet.setBreed(pet.getBreed());
        existingPet.setType(pet.getType());
        return petRepository.save(existingPet);
    }

    @Override
    public void deletePet(Long id) {
        petRepository.findById(id).ifPresentOrElse(petRepository::delete, ()-> {
            throw new ResourceNotFoundException(FeedBackMessage.NOT_FOUND);
        });
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(FeedBackMessage.NOT_FOUND));
    }
}
