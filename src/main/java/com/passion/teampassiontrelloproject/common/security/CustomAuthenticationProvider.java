package com.passion.teampassiontrelloproject.common.security;

import com.passion.teampassiontrelloproject.common.blacklist.repository.UserBlackListRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j(topic = "Authentication 관련 로그")
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final UserBlackListRepository userBlackListRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, UserBlackListRepository userBlackListRepository, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.userBlackListRepository = userBlackListRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails user = (UserDetails) userDetailsService.loadUserByUsername(username);

        try {
            // 비밀번호 비교 로직
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("비밀번호가 일치하지 않습니다.");
            }

            // 블랙리스트에 있는 확인
            if (userBlackListRepository.findByUsername(user.getUsername()).isPresent()) {
                throw new LockedException("차단된 유저입니다.");
            }
        } catch (AuthenticationException e) {
            log.error("인증실패 : " + e.getMessage());
            throw e;
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    private boolean matchPassword(String loginPwd, String password) {
        return loginPwd.equals(password);
    }
}
