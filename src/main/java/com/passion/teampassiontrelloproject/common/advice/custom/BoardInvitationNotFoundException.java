package com.passion.teampassiontrelloproject.common.advice.custom;

public class BoardInvitationNotFoundException extends RuntimeException{
    public BoardInvitationNotFoundException(String message) {
        super(message);
    }
}
