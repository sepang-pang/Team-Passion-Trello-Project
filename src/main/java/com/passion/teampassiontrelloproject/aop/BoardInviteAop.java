package com.passion.teampassiontrelloproject.aop;

import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
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

    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.createColumns(..))")
    private void createColumns() {}

    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.updateColumns(..))")
    private void updateColumns() {}

    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.deleteColumns(..))")
    private void deleteColumns() {}

    @Pointcut("execution(* com.passion.teampassiontrelloproject.board.service.BoardService.*(..))")
    private void boardService() {}

    @Around("cardService()")
    public Object executeAuthorityCard(ProceedingJoinPoint joinPoint) throws Throwable{
        Long cardBoardId = (Long)joinPoint.getArgs()[1];

        // user
        userCheck(cardBoardId);
        return joinPoint.proceed();
    }

    @Around("createColumns() || updateColumns()")
    public Object executeAuthorityColumns(ProceedingJoinPoint joinPoint) throws Throwable{
        ColumnsRequestDto columnsRequestDto = (ColumnsRequestDto) joinPoint.getArgs()[0];
        Long columnsBoardId = columnsRequestDto.getBoardId();

        // user
        userCheck(columnsBoardId);
        return joinPoint.proceed();
    }

    @Around("deleteColumns()")
    public Object executeAuthorityColumnsDelete(ProceedingJoinPoint joinPoint) throws Throwable{
        Columns Columns = (Columns) joinPoint.getArgs()[0];
        Long columnsBoardId = Columns.getBoard().getId();

        // user
        userCheck(columnsBoardId);
        return joinPoint.proceed();
    }



    public void userCheck(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User user = userDetails.getUser();

            userBoardRepository.findByUserIdAndBoardId(user.getId(), id).orElseThrow(() ->
                    new IllegalArgumentException("보드에 속한 유저가 아닙니다."));
        }
    }

}
