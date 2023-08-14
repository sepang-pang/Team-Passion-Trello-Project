package com.passion.teampassiontrelloproject.column.controller;

import com.passion.teampassiontrelloproject.card.dto.CardResponseDto;
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

    // 컬럼 선택조회
    @GetMapping("/columns/{columnsId}")
    public ResponseEntity<ColumnsResponseDto> getColumnsById(@PathVariable Long columnsId) {
        ColumnsResponseDto columns = columnsService.getColumnsById(columnsId);
        if (columns != null) {
            return new ResponseEntity<>(columns, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/columns")
    public ResponseEntity<ColumnsResponseDto> createColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ColumnsRequestDto requestDto) {
        ColumnsResponseDto result = columnsService.createColumns(requestDto, requestDto.getBoardId(), userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/columns/{columnsId}")
    public ResponseEntity<ApiResponseDto> updateColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long columnsId, @RequestBody ColumnsRequestDto requestDto) {
        Columns columns = columnsService.findColumns(columnsId);
        ColumnsResponseDto result = columnsService.updateColumns(requestDto,columns,  userDetails.getUser());

        if (!columns.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 수정 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/columns/{columnsId}")
    public ResponseEntity<ApiResponseDto> deleteColumns(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long columnsId) {
        Columns columns = columnsService.findColumns(columnsId);
        columnsService.deleteColumns(columns, userDetails.getUser());

        if (!columns.getUser().getId().equals(userDetails.getUser().getId())) {
            throw new IllegalArgumentException("작성자만 삭제 할 수 있습니다.");
        }

        return ResponseEntity.ok().body(new ApiResponseDto("컬럼 삭제 성공", HttpStatus.OK.value()));
    }
}
