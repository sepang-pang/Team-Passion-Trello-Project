package com.passion.teampassiontrelloproject.user.service;

import com.passion.teampassiontrelloproject.common.advice.custom.*;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.jwt.JwtUtil;
import com.passion.teampassiontrelloproject.user.change.password.PasswordManager;
import com.passion.teampassiontrelloproject.user.change.password.repository.PasswordManagerRepository;
import com.passion.teampassiontrelloproject.user.dto.ChangePasswordDto;
import com.passion.teampassiontrelloproject.user.dto.CheckPasswordDto;
import com.passion.teampassiontrelloproject.user.dto.SignupRequestDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.entity.UserRoleEnum;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import com.passion.teampassiontrelloproject.withdrawn.service.WithdrawnUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j(topic = "UserService 로그")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final WithdrawnUserService withdrawnUserService;
    private final PasswordManagerRepository passwordManagerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    @Value("${ADMIN_TOKEN}")
    private String adminToken;

    // 회원가입
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());


        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsernameAndIsDeletedFalse(username);
        if (checkUsername.isPresent()) {
            throw new DuplicateException("중복되는 회원입니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new DuplicateException("중복되는 email 입니다");

        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!adminToken.equals(requestDto.getAdminToken())) {
                throw new InvalidAdminTokenException("ADMIN TOKEN 값이 올바르지 않습니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
        log.info("사용자 등록 확인");
        log.info("회원가입 성공");


        // 최초 비밀번호 저장
        PasswordManager passwordManager = new PasswordManager(password, user);
        passwordManagerRepository.save(passwordManager);
        log.info("최초 비밀번호 저장");

        return ResponseEntity.ok().body(new ApiResponseDto("회원가입 성공", 200));
    }


    // 회원탈퇴
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> withdrawal(CheckPasswordDto checkPasswordDto, User user) {

        // 비밀번호 확인, 첫 번째 입력
        if (!passwordEncoder.matches(checkPasswordDto.getFirstPassword(), user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 확인, 두 번째 입력
        if (!passwordEncoder.matches(checkPasswordDto.getSecondPassword(), user.getPassword())) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }

        // 유저의 정보를 백업
        withdrawnUserService.backUpUserDate(user);

        // 탈퇴 메서드
        user.performWithdrawal();

        // DB 반영
        userRepository.save(user);
        return ResponseEntity.ok().body(new ApiResponseDto("회원탈퇴 성공", 200));
    }

    // 로그아웃
    @Override
    public ResponseEntity<ApiResponseDto> logOut(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        if (StringUtils.hasText(token)) {
            jwtUtil.addToBlacklist(token);
        }
        return ResponseEntity.ok().body(new ApiResponseDto("로그아웃 성공", 200));
    }

    // 비밀번호 변경
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> changePassword(ChangePasswordDto changePasswordDto, User user) {

        // 현재 비밀번호 확인
        log.info("현재 비밀번호 확인");
        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())) {
            throw new PasswordMismatchException("현재 비밀번호가 일치하지 않습니다.");
        }

        // 변경 비밀번호 일치 확인
        log.info("비밀번호 일치 확인");
        if (!changePasswordDto.getFirstInputPassword().equals(changePasswordDto.getSecondInputPassword())) {
            throw new PasswordMismatchException("새로운 비밀번호가 일치하지 않습니다");
        }

        // 최근 세 번 안에 사용한 비밀번호 확인
        log.info("비밀번호 변경 내역 조회");
        List<PasswordManager> passwordHistory = passwordManagerRepository.findTop3ByUserOrderByCreatedAtDesc(user);

        // 변경 비밀번호 암호화
        log.info("최근 세 번 안에 이용한 비밀번호 확인");
        for (PasswordManager passwordManager : passwordHistory) {
            String recentPassword = passwordManager.getPassword();
            if (passwordEncoder.matches(changePasswordDto.getSecondInputPassword(), recentPassword)) {
                throw new RecentPasswordException("최근 세 번 안에 사용한 비밀번호입니다.");
            }
        }

        // 변경 비밀번호 암호화
        String newPassword = passwordEncoder.encode(changePasswordDto.getSecondInputPassword());
        user.updatePassword(newPassword);

        // 비밀번호 저장
        log.info("PasswordManager DB 저장");
        PasswordManager passwordManager = new PasswordManager(newPassword, user);

        // DB 저장
        log.info("User DB 저장");
        userRepository.save(user);
        passwordManagerRepository.save(passwordManager);


        // 상태값 반환
        return ResponseEntity.ok().body(new ApiResponseDto("비밀번호 변경 성공", 200));

    }

    // 유저 체크 메서드
    public void authUserCheck (String username) {
        userRepository.findByUsernameAndIsDeletedFalse(username)
                .orElseThrow(() -> new UserNotFoundException("탈퇴한 유저입니다."));
    }
}
