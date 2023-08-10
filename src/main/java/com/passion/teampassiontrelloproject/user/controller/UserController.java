package com.passion.teampassiontrelloproject.user.controller;

import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import com.passion.teampassiontrelloproject.user.dto.ChangePasswordDto;
import com.passion.teampassiontrelloproject.user.dto.CheckPasswordDto;
import com.passion.teampassiontrelloproject.user.dto.SignupRequestDto;
import com.passion.teampassiontrelloproject.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j(topic = "UserController 로그")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(new ApiResponseDto("회원가입 실패", 400));
        }
        return userService.signup(requestDto);
    }

    // 회원탈퇴
    @DeleteMapping("/user/withdrawal")
    public ResponseEntity<ApiResponseDto> withdrawal(@RequestBody CheckPasswordDto checkPasswordDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.withdrawal(checkPasswordDto, userDetails.getUser());
    }

    // 로그아웃
    @DeleteMapping("/user/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletRequest request) {
        return userService.logOut(request);
    }

    // 비밀번호 변경
    @PutMapping("/user/change-password")
    private ResponseEntity<ApiResponseDto> changePassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.changePassword(changePasswordDto, userDetails.getUser());
    }

}
