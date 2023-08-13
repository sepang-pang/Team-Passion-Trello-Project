package com.passion.teampassiontrelloproject.comment.controller;

import com.passion.teampassiontrelloproject.column.dto.ColumnsResponseDto;
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

    // 댓글 선택조회
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> getCommentById(@PathVariable Long commentId) {
        CommentResponseDto comment = commentService.getCommentById(commentId);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto result = commentService.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId, @RequestBody CommentRequestDto requestDto) {
        Comment comment = commentService.findComment(commentId);

        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        CommentResponseDto result = commentService.updateComment(requestDto, comment, userDetails.getUser());
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long commentId) {
        Comment comment = commentService.findComment(commentId);

        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        commentService.deleteComment(comment, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
