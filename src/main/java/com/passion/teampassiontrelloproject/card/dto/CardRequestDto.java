package com.passion.teampassiontrelloproject.card.dto;

import lombok.Getter;

@Getter
public class CardRequestDto {
    private String title;
    private String description;
    private String background_color;
    private Long boardId;
    private Long columnId;
}
