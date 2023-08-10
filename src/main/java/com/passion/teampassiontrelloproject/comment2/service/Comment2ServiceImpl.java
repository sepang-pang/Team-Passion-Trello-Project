package com.passion.teampassiontrelloproject.comment2.service;

import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.service.CardServiceImpl;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.service.CommentServiceImpl;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2RequestDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2ResponseDto;
import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
import com.passion.teampassiontrelloproject.comment2.repository.Comment2Repository;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class Comment2ServiceImpl implements Comment2Service {
    private final Comment2Repository commentRepository;
    private final CommentServiceImpl commentService;

    @Override
    public Comment2ResponseDto createComment(Comment2RequestDto requestDto, User user) {
        Comment comment = commentService.findComment(requestDto.getCommentId());
        Comment2 comment2 = new Comment2();
        comment2.setUser(user);
        comment2.setComment(comment);
        comment2.setDescription2(requestDto.getDescription2());
        comment2.setUsername2(requestDto.getUsername2());

        var savedComment2 = commentRepository.save(comment2);

        return new Comment2ResponseDto(savedComment2);
    }

    @Override
    public void deleteComment(Comment2 comment, User user) {
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public Comment2ResponseDto updateComment(Comment2 comment, Comment2RequestDto requestDto, User user) {

        comment.setUsername2(requestDto.getUsername2());
        comment.setDescription2(requestDto.getDescription2());

        return new Comment2ResponseDto(comment);
    }

    @Override
    public Comment2 findComment(long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다.")
        );
    }
}

