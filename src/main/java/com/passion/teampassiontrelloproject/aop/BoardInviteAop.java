package com.passion.teampassiontrelloproject.aop;

import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j(topic = "BoardInviteAop")
@Aspect
@Component
@RequiredArgsConstructor
public class BoardInviteAop {

    private final UserBoardRepository userBoardRepository;

    @Pointcut("execution(* com.passion.teampassiontrelloproject.card.service.CardService.*(..))")
    private void cardService() {}

    @Around("cardService()")
    public Object executeAuthority(ProceedingJoinPoint joinPoint) throws Throwable{
        Long boardId = (Long)joinPoint.getArgs()[1];

        // user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User user = userDetails.getUser();

            userBoardRepository.findByUserIdAndBoardId(user.getId(), boardId).orElseThrow(() ->
                    new IllegalArgumentException("보드에 속한 유저가 아닙니다."));
        }
        return joinPoint.proceed();
    }
}
