package com.passion.teampassiontrelloproject.column.dto;

import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ColumnsResponseDto extends ApiResponseDto {
    private Long columns_id;
    private String name;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ColumnsResponseDto(Columns columns) {
        super();
        this.columns_id = getColumns_id();
        this.name = columns.getName();
        this.title = columns.getTitle();
        this.description = columns.getDescription();
        this.createdAt = columns.getCreatedAt();
        this.modifiedAt = columns.getModifiedAt();
    }
}
