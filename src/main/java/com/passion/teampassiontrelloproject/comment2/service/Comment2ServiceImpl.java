package com.passion.teampassiontrelloproject.comment2.service;

import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.service.CardServiceImpl;
import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.service.CommentServiceImpl;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2RequestDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2ResponseDto;
import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
import com.passion.teampassiontrelloproject.comment2.repository.Comment2Repository;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Comment2ServiceImpl implements Comment2Service {
    private final Comment2Repository commentRepository;
    private final CommentServiceImpl commentService;
    private final SlackService slackService;

    @Transactional
    @Override
    public Comment2ResponseDto getComment2ById(Long id) {
        Comment2 comment = findComment(id);
        return new Comment2ResponseDto(comment);
    }

    @Override
    public Comment2ResponseDto createComment(Comment2RequestDto requestDto, User user) {
        Comment comment = commentService.findComment(requestDto.getCommentId());
        Comment2 comment2 = new Comment2();
        comment2.setUser(user);
        comment2.setComment(comment);
        comment2.setDescription2(requestDto.getDescription2());
        comment2.setUsername2(requestDto.getUsername2());

        var savedComment2 = commentRepository.save(comment2);

        // 슬랙 메시지 전송
        String message = "새로운 대댓글이 작성되었습니다";
        slackService.sendSlackNotification(message);

        return new Comment2ResponseDto(savedComment2);
    }

    @Override
    public void deleteComment(Comment2 comment, User user) {
        commentRepository.delete(comment);

        // 슬랙 메시지 전송
        String message = "대댓글이 삭제되었습니다";
        slackService.sendSlackNotification(message);
    }

    @Override
    @Transactional
    public Comment2ResponseDto updateComment(Comment2RequestDto requestDto,Comment2 comment,  User user) {

        comment.setUsername2(requestDto.getUsername2());
        comment.setDescription2(requestDto.getDescription2());

        // 슬랙 메시지 전송
        String message = "대댓글이 수정되었습니다";
        slackService.sendSlackNotification(message);

        return new Comment2ResponseDto(comment);
    }

    @Override
    public Comment2 findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}

