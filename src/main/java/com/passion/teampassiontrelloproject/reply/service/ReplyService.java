package com.passion.teampassiontrelloproject.reply.service;

import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyService {

    @Transactional
    ReplyResponseDto getReplyById(Long id);

    ReplyResponseDto createComment(ReplyRequestDto requestDto, User user);

    void deleteComment(Reply comment, User user);

    Reply findComment(long id);

    @Transactional
    ReplyResponseDto updateComment(ReplyRequestDto requestDto, Reply comment, User user);

}
