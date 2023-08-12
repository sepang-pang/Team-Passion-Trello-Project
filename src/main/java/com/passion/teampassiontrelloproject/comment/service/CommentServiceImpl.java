package com.passion.teampassiontrelloproject.comment.service;

import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.card.service.CardServiceImpl;
import com.passion.teampassiontrelloproject.comment.dto.CommentRequestDto;
import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.repository.CommentRepository;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CardServiceImpl cardService;

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

        return new CommentResponseDto(savedComment);
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment, User user) {
        commentRepository.delete(comment);
    }

    @Transactional
    @Override
    public CommentResponseDto updateComment(Comment comment, CommentRequestDto requestDto, User user) {

        comment.setUsername(requestDto.getUsername());
        comment.setDescription(requestDto.getDescription());

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

