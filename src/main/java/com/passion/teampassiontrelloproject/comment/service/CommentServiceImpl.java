package com.passion.teampassiontrelloproject.comment.service;

import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.service.CardServiceImpl;
import com.passion.teampassiontrelloproject.comment.dto.CommentRequestDto;
import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.repository.CommentRepository;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CardServiceImpl cardService;
    private final SlackService slackService;

    @Transactional
    @Override
    public CommentResponseDto getCommentById(Long id) {
        Comment comment = findComment(id);
        return new CommentResponseDto(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {
        Card card = cardService.findCard(requestDto.getCardId());
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setCard(card);
        comment.setDescription(requestDto.getDescription());
        comment.setUsername(requestDto.getUsername());

        var savedComment = commentRepository.save(comment);

        // 슬랙 메시지 전송
        String message = "새로운 댓글이 작성되었습니다";
        slackService.sendSlackNotification(message);

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment, User user) {
        commentRepository.delete(comment);

        // 슬랙 메시지 전송
        String message = "댓글이 삭제되었습니다";
        slackService.sendSlackNotification(message);
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(CommentRequestDto requestDto, Comment comment, User user) {

        comment.setUsername(requestDto.getUsername());
        comment.setDescription(requestDto.getDescription());

        // 슬랙 메시지 전송
        String message = "댓글이 수정되었습니다";
        slackService.sendSlackNotification(message);

        return new CommentResponseDto(comment);
    }

    @Transactional
    @Override
    public Comment findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 card는 존재하지 않습니다.")
        );
    }
}

