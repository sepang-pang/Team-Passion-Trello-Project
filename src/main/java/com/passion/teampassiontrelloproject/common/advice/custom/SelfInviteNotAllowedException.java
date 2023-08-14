package com.passion.teampassiontrelloproject.common.advice.custom;

public class SelfInviteNotAllowedException extends RuntimeException{
    public SelfInviteNotAllowedException(String message) {
        super(message);
    }
}
