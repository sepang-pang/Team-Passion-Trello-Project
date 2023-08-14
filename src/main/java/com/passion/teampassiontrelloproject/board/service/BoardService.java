package com.passion.teampassiontrelloproject.board.service;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.board.dto.BoardResponseDto;
import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardRequestDto;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardResponseDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface BoardService {

    BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user);
    List<BoardResponseDto> getBoard(User user);
    ResponseEntity<ApiResponseDto> getIdBoard(Long id, User user);
    BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, User user);
    ResponseEntity<ApiResponseDto> deleteBoard(Long id, User user);
    ResponseEntity<ApiResponseDto> inviteBoard(UserBoardRequestDto userBoardRequestDto, User user);
    List<UserBoardResponseDto> getInviteUser(Long id);
    ResponseEntity<ApiResponseDto> exceptUserBoard(Long BoardId, Long UserId, User user);
    Board findBoard(Long id);

}
