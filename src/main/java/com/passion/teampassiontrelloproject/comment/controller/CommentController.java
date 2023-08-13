package com.passion.teampassiontrelloproject.comment.controller;

import com.passion.teampassiontrelloproject.comment.dto.CommentRequestDto;
import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.comment.service.CommentServiceImpl;
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
public class CommentController {

    private final CommentServiceImpl commentService;

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto result = commentService.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody CommentRequestDto requestDto) {
        Comment comment = commentService.findComment(id);
        CommentResponseDto result = commentService.updateComment(requestDto, comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Comment comment = commentService.findComment(id);
        commentService.deleteComment(comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
