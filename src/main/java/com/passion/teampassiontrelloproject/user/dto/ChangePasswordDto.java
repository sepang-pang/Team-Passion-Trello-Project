package com.passion.teampassiontrelloproject.user.dto;

import lombok.Getter;

@Getter
public class ChangePasswordDto {
    private String currentPassword; // 현재 비밀번호
    private String firstInputPassword; // 변경 비밀번호
    private String secondInputPassword; // 변경 비밀번호 확인
}
