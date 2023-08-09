package com.passion.teampassiontrelloproject.board.controller;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.board.dto.BoardResponseDto;
import com.passion.teampassiontrelloproject.board.service.BoardService;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.security.UserDetailsImpl;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardRequestDto;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/board") // 보드 작성
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto, userDetails.getUser());
    }

    @PutMapping("/board/{id}") // 보드 수정
    public BoardResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, boardRequestDto, userDetails.getUser());
    }

    @DeleteMapping("/board/{id}") // 보드 삭제
    public ResponseEntity<ApiResponseDto> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    @PostMapping("/board/invite") // 보드 초대
    public ResponseEntity<ApiResponseDto> inviteBoard(@RequestBody UserBoardRequestDto userBoardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.inviteBoard(userBoardRequestDto, userDetails.getUser());
    }

    @GetMapping("/board/invite/{id}") // 보드에 초대된 유저 조회
    public List<UserBoardResponseDto> getInviteUser(@PathVariable Long id) {
        return boardService.getInviteUser(id);
    }
}
