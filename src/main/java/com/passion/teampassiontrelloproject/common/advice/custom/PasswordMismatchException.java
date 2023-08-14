package com.passion.teampassiontrelloproject.common.advice.custom;

public class PasswordMismatchException extends RuntimeException{
    public PasswordMismatchException(String message) {
        super(message);
    }
}
