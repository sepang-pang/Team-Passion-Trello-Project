package com.passion.teampassiontrelloproject.common.advice.custom;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
