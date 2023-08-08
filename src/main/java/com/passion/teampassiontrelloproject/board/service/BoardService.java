package com.passion.teampassiontrelloproject.board.service;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.board.dto.BoardResponseDto;
import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.repository.BoardRepository;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = new Board(boardRequestDto,user);
        board.setUser(user);

        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id,BoardRequestDto boardRequestDto, User user) {
        Board board = findBoard(id);

        board.update(boardRequestDto);
        return new BoardResponseDto(board);

    }

    public ResponseEntity<ApiResponseDto> deleteBoard(Long id, User user) {
        Board board = findBoard(id);
        if(!user.getId().equals(board.getUser().getId())){
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        boardRepository.delete(board);
        return ResponseEntity.ok().body(new ApiResponseDto("보드 삭제 완료!", HttpStatus.OK.value()));
    }

    public Board findBoard(Long id){
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
    }
}
