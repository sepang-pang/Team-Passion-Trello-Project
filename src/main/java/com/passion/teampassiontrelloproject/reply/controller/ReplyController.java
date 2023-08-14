package com.passion.teampassiontrelloproject.reply.controller;

import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.reply.service.ReplyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyServiceImpl replyService;

    // 대댓글 선택조회
    public ResponseEntity<ReplyResponseDto> getReplyById(@PathVariable Long replyId) {
        ReplyResponseDto reply  = replyService.getReplyById(replyId);
        if (reply  != null) {
            return new ResponseEntity<>(reply , HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reply")
    public ResponseEntity<ReplyResponseDto> createReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReplyRequestDto requestDto) {
        ReplyResponseDto result = replyService.createReply(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/reply/{id}")
    public ResponseEntity<ApiResponseDto> updateReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ReplyRequestDto requestDto) {
        Reply reply = replyService.findReply(id);
        ReplyResponseDto result = replyService.updateReply(requestDto, reply, userDetails.getUser());
        if (!reply.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<ApiResponseDto> deleteReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Reply reply = replyService.findReply(id);
        replyService.deleteReply(reply, userDetails.getUser());
        if (!reply.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
