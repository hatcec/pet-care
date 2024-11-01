package com.example.pet_care.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private  String firstName;
    private  String lastName;
    private  String gender;
    @Column(name="mobile")
    private  String phoneNumber;
    private  String email;
    private  String password;
    private  String userType;
    private  boolean isEnabled;
    @CreationTimestamp
    private LocalDate createdAd;
    @Transient
    private  String specialization;
    @Transient
    List<Appointment> appointmentList;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Photo photo;

    public void removeUserPhoto(){
        if(this.getPhoto()!=null){
            this.setPhoto(null);
        }
    }
}
