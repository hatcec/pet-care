package com.example.pet_care.factory;

import com.example.pet_care.entities.Patient;
import com.example.pet_care.entities.User;
import com.example.pet_care.repository.PatientRepository;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientFactory {
    private final PatientRepository patientRepository;
    private final UserAttributesMapper userAttributesMapper;
    public Patient createPatient(RegistrationRequest registrationRequest) {
        Patient patient=new Patient();
        userAttributesMapper.setCommonAttributes(registrationRequest,patient);
        return patientRepository.save(patient);
    }
}
