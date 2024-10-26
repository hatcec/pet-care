package com.example.pet_care.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private  String fistName;
    private  String lastName;
    private  String gender;
    @Column(name="mobile")
    private  String phoneNumber;
    private  String email;
    private  String password;
    private  String userType;
    private  boolean isEnabled;
}
