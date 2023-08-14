package com.passion.teampassiontrelloproject.reply.service;

import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.service.CommentServiceImpl;
import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.reply.repository.ReplyRepository;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final CommentServiceImpl commentService;

    @Transactional
    @Override
    public ReplyResponseDto getReplyById(Long id) {
        Reply reply = findReply(id);
        return new ReplyResponseDto(reply);
    }

    @Override
    public ReplyResponseDto createReply(ReplyRequestDto requestDto, User user) {
        Comment comment = commentService.findComment(requestDto.getCommentId());
        Reply reply = new Reply();
        reply.setUser(user);
        reply.setComment(comment);
        reply.setDescription(requestDto.getDescription());
        reply.setUsername(requestDto.getUsername());

        var savedReply = replyRepository.save(reply);

        return new ReplyResponseDto(savedReply);
    }

    @Override
    public void deleteReply(Reply reply, User user) {
        replyRepository.delete(reply);
    }

    @Override
    @Transactional
    public ReplyResponseDto updateReply(ReplyRequestDto requestDto, Reply reply, User user) {

        reply.setUsername(requestDto.getUsername());
        reply.setDescription(requestDto.getDescription());

        return new ReplyResponseDto(reply);
    }

    @Override
    public Reply findReply(long id) {
        return replyRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}

