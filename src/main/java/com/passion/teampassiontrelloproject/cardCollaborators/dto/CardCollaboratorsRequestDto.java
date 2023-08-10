package com.passion.teampassiontrelloproject.cardCollaborators.dto;

import lombok.Getter;

@Getter
public class CardCollaboratorsRequestDto {
    private String username;
    private Long cardId;
    private Long boardId;
}
