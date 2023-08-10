package com.passion.teampassiontrelloproject.card.dto;

import com.passion.teampassiontrelloproject.card.entity.Card;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class CardResponseDto {
    private Long card_id;
    private String title;
    private String username;
    private String description;
    private String background_color;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    public CardResponseDto(Card card) {
        this.card_id = getCard_id();
        this.title = card.getTitle();
        this.username = card.getUsername();
        this.description = card.getDescription();
        this.background_color = card.getBackground_color();
        this.createdAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();
    }
}
