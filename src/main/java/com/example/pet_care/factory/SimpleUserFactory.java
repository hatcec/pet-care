package com.example.pet_care.factory;

import com.example.pet_care.entities.User;
import com.example.pet_care.exception.UserAllReadyExistsException;
import com.example.pet_care.repository.UserRepository;
import com.example.pet_care.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SimpleUserFactory implements UserFactory{

    private final UserRepository userRepository;
    private final VeterinarianFactory veterinarianFactory;
    private final PatientFactory patientFactory;
    private final AdminFactory adminFactory;
    @Override
    public User createUser(RegistrationRequest registrationRequest) {
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new UserAllReadyExistsException("Oops!"+ registrationRequest.getEmail()+" already exists!");
        }
        switch (registrationRequest.getUserType()){
            case "VET"->{
                return veterinarianFactory.createVeterinarian(registrationRequest);
            }
            case "PATIENT" ->{
                return patientFactory.createPatient(registrationRequest);
            }
            case "ADMIN" ->{
                return adminFactory.createAdmin(registrationRequest);
            }
            default -> {
                return null;
            }
        }
    }
}
