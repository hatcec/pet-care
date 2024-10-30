package com.example.pet_care.request;

import com.example.pet_care.entities.Appointment;
import com.example.pet_care.entities.Pet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookAppointmentRequest {
    private Appointment appointment;
    private List<Pet> petList;
}
