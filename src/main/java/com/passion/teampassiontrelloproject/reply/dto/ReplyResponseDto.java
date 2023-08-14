package com.passion.teampassiontrelloproject.reply.dto;

import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReplyResponseDto extends ApiResponseDto {
    private String username2;
    private String description2;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ReplyResponseDto(Reply reply) {
        super();
        this.username2 = reply.getUsername2();
        this.description2 = reply.getDescription2();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
