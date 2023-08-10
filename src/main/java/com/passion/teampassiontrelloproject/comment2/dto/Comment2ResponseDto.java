package com.passion.teampassiontrelloproject.comment2.dto;

import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Comment2ResponseDto extends ApiResponseDto {
    private String username2;
    private String description2;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public Comment2ResponseDto(Comment2 comment) {
        super();
        this.username2 = comment.getUsername2();
        this.description2 = comment.getDescription2();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}
