package com.passion.teampassiontrelloproject.user.service;


import com.passion.teampassiontrelloproject.common.advice.custom.DuplicateException;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.jwt.JwtUtil;
import com.passion.teampassiontrelloproject.user.change.password.PasswordManager;
import com.passion.teampassiontrelloproject.user.change.password.repository.PasswordManagerRepository;
import com.passion.teampassiontrelloproject.user.dto.ChangePasswordDto;
import com.passion.teampassiontrelloproject.user.dto.SignupRequestDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.entity.UserRoleEnum;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


public interface UserService {

    // 회원가입
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto);

    // 로그아웃
    public ResponseEntity<ApiResponseDto> logOut(HttpServletRequest request);

    // 비밀번호 변경
    public ResponseEntity<ApiResponseDto> changePassword(ChangePasswordDto changePasswordDto, User user);
}
