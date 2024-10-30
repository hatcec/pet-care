package com.example.pet_care.utils;

public class UrlMapping {
    public static final String API = "/api";
    public static final String USERS = API+"/users";
    public static final String REGISTER_USER = "/register";
    public static final String UPDATE_USER = "/update/{userId}";
    public static final String GET_USER_BY_ID = "/user/{userId}";
    public static final String DELETE_USER_BY_ID ="/delete/{userId}" ;
    public static final String GET_ALL_USERS = "/allusers";


    /*---------------------------*/


    public static final String APPOINTMENT =API+ "/appointments";
    public static final String GET_ALL_APPOINTMENT = "/all";
    public static final String BOOK_APPOINTMENT = "/book-appointment";
    public static final String GET_APPOINTMENT_BY_ID ="/appointment/{id}" ;
    public static final String APPOINTMENT_GET_BY_NO ="/appointment/{appointmentNo}/getappointment" ;
    public static final String DELETE_APPOINTMENT ="/appointment/{id}/delete" ;
    public static final String UPDATE_APPOINTMENT = "/appointment/{id}/update";



    /*------------------------*/
    public static final String PETS =API+ "/pets";
    public static final String SAVE_PETS_APPOINTMENT = "/save-pet-appointment";
    public static final String GET_PET_BY_ID ="/getPetById/{id}" ;
    public static final String DELETE_PET_BY_ID = "/deletePetById/{id}";
    public static final String UPDATE_PET = "/update/id";
}
