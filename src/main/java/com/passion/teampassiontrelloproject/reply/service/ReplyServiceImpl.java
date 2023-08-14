package com.passion.teampassiontrelloproject.reply.service;

import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.service.CommentServiceImpl;
import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.reply.repository.ReplyRepository;
import com.passion.teampassiontrelloproject.common.advice.custom.CardNotFoundException;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository commentRepository;
    private final CommentServiceImpl commentService;
    private final SlackService slackService;

    @Transactional
    @Override
    public ReplyResponseDto getReplyById(Long id) {
        Reply comment = findComment(id);
        return new ReplyResponseDto(comment);
    }

    @Override
    public ReplyResponseDto createComment(ReplyRequestDto requestDto, User user) {
        Comment comment = commentService.findComment(requestDto.getCommentId());
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setComment(comment);
        reply.setDescription2(requestDto.getDescription2());
        reply.setUsername2(requestDto.getUsername2());

        var savedReply = commentRepository.save(reply);

        // 슬랙 메시지 전송
        String message = "새로운 대댓글이 작성되었습니다";
        slackService.sendSlackNotification(message);

        return new ReplyResponseDto(savedReply);
    }

    @Override
    public void deleteComment(Reply comment, User user) {
        commentRepository.delete(comment);

        // 슬랙 메시지 전송
        String message = "대댓글이 삭제되었습니다";
        slackService.sendSlackNotification(message);
    }

    @Override
    @Transactional
    public ReplyResponseDto updateComment(ReplyRequestDto requestDto, Reply comment, User user) {

        comment.setUsername2(requestDto.getUsername2());
        comment.setDescription2(requestDto.getDescription2());

        // 슬랙 메시지 전송
        String message = "대댓글이 수정되었습니다";
        slackService.sendSlackNotification(message);

        return new ReplyResponseDto(comment);
    }

    @Override
    public Reply findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new CardNotFoundException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}

