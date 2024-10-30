package com.example.pet_care.entities;

import com.example.pet_care.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;


@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"patient", "veterinarian"})
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String reason;
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
    private String appointmentNo;

    @CreationTimestamp
    private LocalDate createdAt;
    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;
    @JoinColumn(name="sender")
    @ManyToOne(fetch = FetchType.LAZY)
    private  User patient;

    @JoinColumn(name="recipient")
    @ManyToOne(fetch = FetchType.LAZY)
    private  User veterinarian;

    //randevu listesini kontrol edip boşsa randevu oluşturulacak.
    public void addPatient(User sender){
        this.setPatient(sender);
        if(sender.getAppointmentList() == null){
            sender.setAppointmentList(new ArrayList<>());
        }
        sender.getAppointmentList().add(this);
    }

    public void addVeterinarian(User recipent){
        this.setVeterinarian(recipent);
        if(recipent.getAppointmentList() == null){
            recipent.setAppointmentList(new ArrayList<>());
        }
        recipent.getAppointmentList().add(this);
    }

    public void setAppointmentNo(){
        this.appointmentNo=String.valueOf(new Random().nextLong()).substring(1,11);
    }

}
