package com.example.pet_care.exception;

public class UserAllReadyExistsException extends RuntimeException {
    public UserAllReadyExistsException(String message) {
        super(message);
    }
}
