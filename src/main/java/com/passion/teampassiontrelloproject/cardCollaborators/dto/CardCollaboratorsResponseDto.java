package com.passion.teampassiontrelloproject.cardCollaborators.dto;

import lombok.Getter;

@Getter
public class CardCollaboratorsResponseDto {
    private String username;

    public CardCollaboratorsResponseDto(String username) {
        this.username = username;
    }
}
