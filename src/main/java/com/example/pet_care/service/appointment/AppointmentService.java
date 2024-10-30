package com.example.pet_care.service.appointment;

import com.example.pet_care.entities.Appointment;
import com.example.pet_care.request.AppointmentRequest;
import com.example.pet_care.request.AppointmentUpdateRequest;

import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Appointment appointment, Long senderId, Long recipientId);
    List<Appointment> getAllAppointment();
    Appointment getAppointment(Long id);
    Appointment updateAppointment(Long id, AppointmentUpdateRequest appointmentUpdateRequest);
    void deleteAppointment(Long id);
    Appointment getAppointmentById(Long id);
    Appointment getAppointmentByNo(String appointmentNo);
}