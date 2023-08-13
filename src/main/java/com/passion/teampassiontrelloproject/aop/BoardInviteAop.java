package com.passion.teampassiontrelloproject.aop;

import com.passion.teampassiontrelloproject.board.repository.BoardRepository;
import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.repository.CardRepository;
import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.repository.ColumnsRepository;
import com.passion.teampassiontrelloproject.comment.dto.CommentRequestDto;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.repository.CommentRepository;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2RequestDto;
import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
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

import java.util.Optional;

@Slf4j(topic = "BoardInviteAop")
@Aspect
@Component
@RequiredArgsConstructor
public class BoardInviteAop {

    private final UserBoardRepository userBoardRepository;
    private final CardRepository cardRepository;
    private final ColumnsRepository columnsRepository;
    private final CommentRepository commentRepository;

    // cardService 전체 메서드
    @Pointcut("execution(* com.passion.teampassiontrelloproject.card.service.CardService.*(..))")
    private void cardService() {}

    // createColumns
    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.createColumns(..))")
    private void createColumns() {}

    // updateColumns
    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.updateColumns(..))")
    private void updateColumns() {}

    // deleteColumns
    @Pointcut("execution(* com.passion.teampassiontrelloproject.column.service.ColumnsService.deleteColumns(..))")
    private void deleteColumns() {}

    // createComment
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment.service.CommentService.createComment(..))")
    private void createComment() {}

    // updateComment
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment.service.CommentService.updateComment(..))")
    private void updateComment() {}

    // deleteComment
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment.service.CommentService.deleteComment(..))")
    private void deleteComment() {}

    // createComment2
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment2.service.Comment2Service.createComment(..))")
    private void createComment2() {}

    // updateComment2
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment2.service.Comment2Service.updateComment(..))")
    private void updateComment2() {}

    // deleteComment2
    @Pointcut("execution(* com.passion.teampassiontrelloproject.comment2.service.Comment2Service.deleteComment(..))")
    private void deleteComment2() {}

    // cardService board 초대 여부 확인
    @Around("cardService()")
    public Object executeAuthorityCard(ProceedingJoinPoint joinPoint) throws Throwable{
        Long cardBoardId = (Long)joinPoint.getArgs()[1];

        // user
        userCheck(cardBoardId);
        return joinPoint.proceed();
    }

    // createColumns, updateColumns board 초대 여부 확인
    @Around("createColumns() || updateColumns()")
    public Object executeAuthorityColumns(ProceedingJoinPoint joinPoint) throws Throwable{
        ColumnsRequestDto columnsRequestDto = (ColumnsRequestDto) joinPoint.getArgs()[0];
        Long columnsBoardId = columnsRequestDto.getBoardId();

        // user
        userCheck(columnsBoardId);
        return joinPoint.proceed();
    }

    // deleteColumns board 초대 여부 확인
    @Around("deleteColumns()")
    public Object executeAuthorityColumnsDelete(ProceedingJoinPoint joinPoint) throws Throwable{
        Columns Columns = (Columns) joinPoint.getArgs()[0];
        Long columnsBoardId = Columns.getBoard().getId();

        // user
        userCheck(columnsBoardId);
        return joinPoint.proceed();
    }

    // createComment, updateComment  board 초대 여부 확인
    @Around("createComment() || updateComment()")
    public Object executeAuthorityComment(ProceedingJoinPoint joinPoint) throws Throwable{
        CommentRequestDto commentRequestDto = (CommentRequestDto)joinPoint.getArgs()[0];

        // user
        userCheck(commentBoard(commentRequestDto.getCardId()));
        return joinPoint.proceed();
    }

    // deleteComment board 초대 여부 확인
    @Around("deleteComment()")
    public Object executeAuthorityCommentDelete(ProceedingJoinPoint joinPoint) throws Throwable{
        Comment comment = (Comment)joinPoint.getArgs()[0];

        // user
        userCheck(commentBoard(comment.getCard().getCard_id()));
        return joinPoint.proceed();
    }

    // createComment2, updateComment2  board 초대 여부 확인
    @Around("createComment2() || updateComment2()")
    public Object executeAuthorityComment2(ProceedingJoinPoint joinPoint) throws Throwable{
        Comment2RequestDto comment2RequestDto = (Comment2RequestDto)joinPoint.getArgs()[0];

        // user
        userCheck(commentBoard2(comment2RequestDto.getCommentId()));
        return joinPoint.proceed();
    }

    // deleteComment2 board 초대 여부 확인
    @Around("deleteComment2()")
    public Object executeAuthorityCommentDelete2(ProceedingJoinPoint joinPoint) throws Throwable{
        Comment2 comment2 = (Comment2)joinPoint.getArgs()[0];

        // user
        userCheck(commentBoard2(comment2.getComment().getId()));
        return joinPoint.proceed();
    }


    // userDetails.getUser와 boardId를 통해 userBoard에서 확인
    public void userCheck(Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.getPrincipal().getClass() == UserDetailsImpl.class){
            UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
            User user = userDetails.getUser();

            userBoardRepository.findByUserIdAndBoardId(user.getId(), id).orElseThrow(() ->
                    new IllegalArgumentException("보드에 속한 유저가 아닙니다."));
        }
    }

    // comment 에서 boardId 찾기
    public Long commentBoard(Long id){
        Optional<Card> card = cardRepository.findById(id);
        Optional<Columns> columns = columnsRepository.findById(card.get().getColumns().getId());
        return columns.get().getBoard().getId();
    }

    // comment2 에서 boardId 찾기
    public Long commentBoard2(Long id){
        Optional<Comment> comment = commentRepository.findById(id);
        Optional<Card> card = cardRepository.findById(comment.get().getCard().getCard_id());
        Optional<Columns> columns = columnsRepository.findById(card.get().getColumns().getId());
        return columns.get().getBoard().getId();
    }


}
