package com.passion.teampassiontrelloproject.comment2.service;

import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2RequestDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2ResponseDto;
import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface Comment2Service {

    @Transactional
    Comment2ResponseDto getComment2ById(Long id);

    Comment2ResponseDto createComment(Comment2RequestDto requestDto, User user);

    void deleteComment(Comment2 comment, User user);

    Comment2 findComment(long id);

    @Transactional
    Comment2ResponseDto updateComment(Comment2RequestDto requestDto, Comment2 comment, User user);

}
