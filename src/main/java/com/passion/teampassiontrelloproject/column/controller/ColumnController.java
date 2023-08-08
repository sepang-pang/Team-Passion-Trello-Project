package com.passion.teampassiontrelloproject.column.controller;

import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.dto.ColumnsResponseDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.service.ColumnsServiceImpl;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnsServiceImpl columnsService;

    @PostMapping("/columns")
    public ResponseEntity<ColumnsResponseDto> createColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ColumnsResponseDto requestDto) {
        ColumnsResponseDto result= columnsService.createColumns(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/columns/{id}")
    public ResponseEntity<ApiResponseDto> updateColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ColumnsRequestDto requestDto) {
        try {
            Columns columns = columnsService.findColumns(id);
            ColumnsResponseDto result = columnsService.updateColumns(columns, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(result);
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 수정 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/columns/{id}")
    public ResponseEntity<ApiResponseDto> deleteColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        try {
            Columns columns = columnsService.findColumns(id);
            columnsService.deleteColumns(columns, userDetails.getUser());
            return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 성공", HttpStatus.OK.value()));
        } catch (RejectedExecutionException e) {
            return ResponseEntity.badRequest().body(new ApiResponseDto("작성자만 삭제 할 수 있습니다.", HttpStatus.BAD_REQUEST.value()));
        }
    }
}
