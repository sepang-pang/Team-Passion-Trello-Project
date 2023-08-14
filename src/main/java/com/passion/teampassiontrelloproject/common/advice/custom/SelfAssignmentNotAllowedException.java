package com.passion.teampassiontrelloproject.common.advice.custom;

public class SelfAssignmentNotAllowedException extends RuntimeException{
    public SelfAssignmentNotAllowedException(String message) {
        super(message);
    }
}
