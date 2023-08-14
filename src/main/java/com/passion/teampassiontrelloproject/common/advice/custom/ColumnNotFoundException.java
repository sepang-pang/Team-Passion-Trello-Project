package com.passion.teampassiontrelloproject.common.advice.custom;

public class ColumnNotFoundException extends RuntimeException{
    public ColumnNotFoundException(String message) {
        super(message);
    }
}
