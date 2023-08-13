package com.passion.teampassiontrelloproject.comment2.controller;

import com.passion.teampassiontrelloproject.comment.dto.CommentResponseDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2RequestDto;
import com.passion.teampassiontrelloproject.comment2.dto.Comment2ResponseDto;
import com.passion.teampassiontrelloproject.comment2.entity.Comment2;
import com.passion.teampassiontrelloproject.comment2.service.Comment2ServiceImpl;
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

    private final Comment2ServiceImpl commentService;

    // 대댓글 선택조회
    @GetMapping("/comment2/{comment2Id}")
    public ResponseEntity<Comment2ResponseDto> getComment2ById(@PathVariable Long comment2Id) {
        Comment2ResponseDto comment = commentService.getComment2ById(comment2Id);
        if (comment != null) {
            return new ResponseEntity<>(comment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/comment2")
    public ResponseEntity<Comment2ResponseDto> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody Comment2RequestDto requestDto) {
        Comment2ResponseDto result = commentService.createComment(requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/comment2/{id}")
    public ResponseEntity<ApiResponseDto> updateComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody Comment2RequestDto requestDto) {
        Comment2 comment = commentService.findComment(id);
        Comment2ResponseDto result = commentService.updateComment(requestDto, comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/comment2/{id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        Comment2 comment = commentService.findComment(id);
        commentService.deleteComment(comment, userDetails.getUser());
        if (!comment.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
    }
}
