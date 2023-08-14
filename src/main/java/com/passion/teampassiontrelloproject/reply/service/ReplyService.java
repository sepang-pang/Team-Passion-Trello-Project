package com.passion.teampassiontrelloproject.reply.service;

import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyService {

    @Transactional
    ReplyResponseDto getReplyById(Long id);

    ReplyResponseDto createReply(ReplyRequestDto requestDto, User user);

    void deleteReply(Reply reply, User user);

    Reply findReply(long id);

    @Transactional
    ReplyResponseDto updateReply(ReplyRequestDto requestDto, Reply reply, User user);

}
