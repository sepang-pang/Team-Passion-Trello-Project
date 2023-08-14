package com.passion.teampassiontrelloproject.user.service;


import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.dto.ChangePasswordDto;
import com.passion.teampassiontrelloproject.user.dto.CheckPasswordDto;
import com.passion.teampassiontrelloproject.user.dto.SignupRequestDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;


public interface UserService {

    // 회원가입
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);

    // 회원탈퇴
    public ResponseEntity<ApiResponseDto> withdrawal(CheckPasswordDto checkPasswordDto, User user);

    // 로그아웃
    public ResponseEntity<ApiResponseDto> logOut(HttpServletRequest request);

    // 비밀번호 변경
    public ResponseEntity<ApiResponseDto> changePassword(ChangePasswordDto changePasswordDto, User user);

    // 회원 조회
    public void authUserCheck (String username);

}
