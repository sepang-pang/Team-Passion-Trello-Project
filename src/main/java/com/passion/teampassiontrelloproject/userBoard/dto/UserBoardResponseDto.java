package com.passion.teampassiontrelloproject.userBoard.dto;

import lombok.Getter;

@Getter
public class UserBoardResponseDto {
    private String username;

    public UserBoardResponseDto(String username) {
        this.username = username;
    }
}
