package com.FinalExam.FinalExam.exception;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(Long id){
        super("could not find category with id" +id);
    }
}
