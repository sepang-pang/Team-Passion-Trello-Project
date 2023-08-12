package com.passion.teampassiontrelloproject.card.dto;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class CardRequestDto {
    private String title;
    private String description;
    private String background_color;
    private Long boardId;
    private Long columnId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dueDate;
}
