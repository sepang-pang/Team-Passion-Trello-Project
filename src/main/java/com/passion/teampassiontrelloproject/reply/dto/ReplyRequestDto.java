package com.passion.teampassiontrelloproject.reply.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRequestDto {
    private Long commentId;
    private String username;
    private String description;
}
