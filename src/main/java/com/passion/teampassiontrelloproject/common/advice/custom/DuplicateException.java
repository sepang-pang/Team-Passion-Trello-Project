package com.passion.teampassiontrelloproject.common.advice.custom;

public class DuplicateException extends RuntimeException{
    public DuplicateException(String message) {
        super(message);
    }
}
