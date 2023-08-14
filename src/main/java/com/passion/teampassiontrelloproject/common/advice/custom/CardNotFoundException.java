package com.passion.teampassiontrelloproject.common.advice.custom;

public class CardNotFoundException extends RuntimeException{
    public CardNotFoundException(String message) {
        super(message);
    }
}
