package com.example.pet_care.service.user;

import com.example.pet_care.entities.User;
import com.example.pet_care.request.RegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class UserAttributesMapper {
    public void setCommonAttributes(RegistrationRequest source, User target){
        target.setFirstName(source.getFirstName());
        target.setLastName(source.getLastName());
        target.setGender(source.getGender());
        target.setPhoneNumber(source.getPhoneNumber());
        target.setEmail(source.getEmail());
        target.setEnabled(source.isEnabled());
        target.setUserType(source.getUserType());
        target.setPassword(source.getPassword());
    }
}
