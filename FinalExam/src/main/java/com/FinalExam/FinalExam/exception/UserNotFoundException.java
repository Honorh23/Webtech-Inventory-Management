package com.FinalExam.FinalExam.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(Long id){
        super("could not find User with id" +id);
    }
}
