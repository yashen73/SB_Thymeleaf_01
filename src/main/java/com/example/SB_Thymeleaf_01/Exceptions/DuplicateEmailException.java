package com.example.SB_Thymeleaf_01.Exceptions;

public class DuplicateEmailException  extends RuntimeException{
    public DuplicateEmailException(String message){
        super(message);
    }
}
