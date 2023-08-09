package com.passion.teampassiontrelloproject.column.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ColumnsRequestDto {
    private Long boardId;
    private String name;
    private String title;
    private String description;
}
