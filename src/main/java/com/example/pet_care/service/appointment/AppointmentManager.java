package com.example.pet_care.service.appointment;

import com.example.pet_care.entities.Appointment;
import com.example.pet_care.entities.Pet;
import com.example.pet_care.entities.User;
import com.example.pet_care.enums.AppointmentStatus;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.repository.AppointmentRepository;
import com.example.pet_care.repository.PetRepository;
import com.example.pet_care.repository.UserRepository;
import com.example.pet_care.request.AppointmentRequest;
import com.example.pet_care.request.AppointmentUpdateRequest;
import com.example.pet_care.request.BookAppointmentRequest;
import com.example.pet_care.service.pet.PetService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentManager implements  AppointmentService{
    private final AppointmentRepository appointmentRepository;
    private  final UserRepository userRepository;
    private final PetService petService;

    @Transactional
    @Override
    public Appointment createAppointment(BookAppointmentRequest appointmentRequest, Long senderId, Long recipientId) {
        Optional<User> sender=userRepository.findById(senderId);
        Optional<User> recipient=userRepository.findById(recipientId);
        if(sender.isPresent() && recipient.isPresent()){
            Appointment appointment=appointmentRequest.getAppointment();
            List<Pet> pets=appointmentRequest.getPetList();
            pets.forEach(pet->pet.setAppointment(appointment));
            List<Pet> savedPets=petService.savePetForAppointment(pets);
            appointment.setPetList(savedPets);
            appointment.addPatient(sender.get());
            appointment.addVeterinarian(recipient.get());
            appointment.setAppointmentNo();
            appointment.setAppointmentStatus(AppointmentStatus.WAITING_FOR_APPROVAL);
            return appointmentRepository.save(appointment);

        }
        throw new ResourceNotFoundException("Sender or recipient not found");
    }

    @Override
    public List<Appointment> getAllAppointment() {
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointment(Long id) {
        return null;
    }

    @Override
    public Appointment updateAppointment(Long id, AppointmentUpdateRequest appointmentUpdateRequest) {
        Appointment existingAppointment=getAppointmentById(id);
        if(!Objects.equals(existingAppointment.getAppointmentStatus(), AppointmentStatus.WAITING_FOR_APPROVAL)){
            throw new IllegalStateException("Sorry, this appointment can no longer be updated");
        }
        existingAppointment.setDate(LocalDate.parse(appointmentUpdateRequest.getDate()));
        existingAppointment.setTime(LocalTime.parse(appointmentUpdateRequest.getTime()));
        existingAppointment.setReason(appointmentUpdateRequest.getReason());
        return appointmentRepository.save(existingAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.findById(id).ifPresentOrElse(appointmentRepository::delete, ()->{
            throw  new ResourceNotFoundException("appoint not found");
        });
    }

    @Override
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Appointment not found"));
    }

    @Override
    public Appointment getAppointmentByNo(String appointmentNo) {
        return appointmentRepository.findByAppointmentNo(appointmentNo);
    }
}
