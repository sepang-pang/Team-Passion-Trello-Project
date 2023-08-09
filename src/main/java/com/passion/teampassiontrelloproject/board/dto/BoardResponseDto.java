package com.passion.teampassiontrelloproject.board.dto;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import lombok.Getter;

@Getter
public class BoardResponseDto extends ApiResponseDto {

    private Long id;
    private String name;
    private String description;
    private String backgroundColor;
    private String title;

    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.backgroundColor = board.getBackgroundColor();
        this.title = board.getTitle();
    }
}
