package com.example.pet_care.factory;

import com.example.pet_care.entities.Admin;
import com.example.pet_care.entities.Patient;
import com.example.pet_care.entities.User;
import com.example.pet_care.repository.AdminRepository;
import com.example.pet_care.request.RegistrationRequest;
import com.example.pet_care.service.user.UserAttributesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminFactory {
    private  final AdminRepository adminRepository;
    private final UserAttributesMapper userAttributesMapper;
    public Admin createAdmin(RegistrationRequest registrationRequest) {
        Admin admin=new Admin();
        userAttributesMapper.setCommonAttributes(registrationRequest,admin);
        return  adminRepository.save(admin);
    }
}
