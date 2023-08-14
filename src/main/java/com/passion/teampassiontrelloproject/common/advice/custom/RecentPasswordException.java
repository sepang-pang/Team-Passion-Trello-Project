package com.passion.teampassiontrelloproject.common.advice.custom;

public class RecentPasswordException extends RuntimeException{
    public RecentPasswordException(String message) {
        super(message);
    }
}
