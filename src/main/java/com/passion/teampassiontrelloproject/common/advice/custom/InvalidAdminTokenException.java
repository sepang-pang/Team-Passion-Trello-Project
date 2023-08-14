package com.passion.teampassiontrelloproject.common.advice.custom;

public class InvalidAdminTokenException extends RuntimeException{
    public InvalidAdminTokenException(String message) {
        super(message);
    }
}
