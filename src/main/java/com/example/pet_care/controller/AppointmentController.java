package com.example.pet_care.controller;

import com.example.pet_care.entities.Appointment;
import com.example.pet_care.exception.ResourceNotFoundException;
import com.example.pet_care.request.AppointmentUpdateRequest;
import com.example.pet_care.response.ApiResponse;
import com.example.pet_care.service.appointment.AppointmentService;
import com.example.pet_care.utils.FeedBackMessage;
import com.example.pet_care.utils.UrlMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(UrlMapping.APPOINTMENT)
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping(UrlMapping.GET_ALL_APPOINTMENT)
    public ResponseEntity<ApiResponse> getAllAppointments(){
        try{
            List<Appointment> appointmentList=appointmentService.getAllAppointment();
            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedBackMessage.FOUND, appointmentList));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping(UrlMapping.BOOK_APPOINTMENT)
    public ResponseEntity<ApiResponse> bookAppointment(@RequestBody Appointment appointment,
    @RequestParam Long senderId, @RequestParam Long recipientId ){
        try{
            Appointment theAppointment=appointmentService.createAppointment(appointment, senderId, recipientId);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.SUCCESS, theAppointment));

        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(UrlMapping.GET_APPOINTMENT_BY_ID)
    public ResponseEntity<ApiResponse> getAppointmentById(@PathVariable Long id){
        try{
            Appointment appointment=appointmentService.getAppointmentById(id);
            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedBackMessage.FOUND, appointment));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping(UrlMapping.APPOINTMENT_GET_BY_NO)
    public ResponseEntity<ApiResponse> getAppointmentByNo(@PathVariable String appointmentNo){
        try{
            Appointment appointment=appointmentService.getAppointmentByNo(appointmentNo);
            return ResponseEntity.status(FOUND).body(new ApiResponse(FeedBackMessage.FOUND, appointment));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping(UrlMapping.DELETE_APPOINTMENT)
    public ResponseEntity<ApiResponse> deleteAppointmentById(@PathVariable Long id){
        try {
            appointmentService.getAppointmentById(id);
            return ResponseEntity.ok(new ApiResponse(FeedBackMessage.DELETE_SUCCESS, null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping(UrlMapping.UPDATE_APPOINTMENT)
    public ResponseEntity<ApiResponse> updateAppointment(@PathVariable  Long id, @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        try{
        Appointment appointment= appointmentService.updateAppointment(id, appointmentUpdateRequest);
        return ResponseEntity.ok(new ApiResponse(FeedBackMessage.UPDATE_SUCCESS, appointment));
        }catch (IllegalStateException e){
            return ResponseEntity.status(NOT_ACCEPTABLE).body(new ApiResponse(e.getMessage(),null));
        }
    }

}
