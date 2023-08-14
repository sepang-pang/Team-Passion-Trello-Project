package com.passion.teampassiontrelloproject.reply.controller;

import com.passion.teampassiontrelloproject.reply.dto.ReplyRequestDto;
import com.passion.teampassiontrelloproject.reply.dto.ReplyResponseDto;
import com.passion.teampassiontrelloproject.reply.entity.Reply;
import com.passion.teampassiontrelloproject.reply.service.ReplyServiceImpl;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Comment2Controller {

    private final ReplyServiceImpl commentService;

    // 대댓글 선택조회
    @GetMapping("/reply/{replyId}")
    public ResponseEntity<ReplyResponseDto> getReplyById(@PathVariable Long replyId) {
        ReplyResponseDto comment = commentService.getReplyById(replyId);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/reply")
    public ResponseEntity<ReplyResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReplyRequestDto requestDto) {
        ReplyResponseDto result = commentService.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/reply/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ReplyRequestDto requestDto) {
        Reply comment = commentService.findComment(id);
        ReplyResponseDto result = commentService.updateComment(requestDto, comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Reply comment = commentService.findComment(id);
        commentService.deleteComment(comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
