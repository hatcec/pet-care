package com.example.pet_care.repository;

import com.example.pet_care.entities.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>
{


}
