package com.example.pet_care.factory;

import com.example.pet_care.entities.User;
import com.example.pet_care.entities.Veterinarian;
import com.example.pet_care.repository.VeterinarianRepository;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VeterinarianFactory {
    private  final VeterinarianRepository veterinarianRepository;
    private final UserAttributesMapper userAttributesMapper;

    public Veterinarian createVeterinarian(RegistrationRequest registrationRequest) {
        Veterinarian veterinarian=new Veterinarian();
        userAttributesMapper.setCommonAttributes(registrationRequest, veterinarian);
        veterinarian.setSpecialization(registrationRequest.getSpecialization());
        return veterinarianRepository.save(veterinarian);
    }
}
