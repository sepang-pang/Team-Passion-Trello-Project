package com.passion.teampassiontrelloproject.board.service;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.board.dto.BoardResponseDto;
import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.repository.BoardRepository;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardRequestDto;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        // 보드 내용 삽입
        Board board = new Board(boardRequestDto, user);

        // 유저 보드 관계 매핑
        new UserBoard(user, board);

        // db 저장
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, User user) {
        // 수정할 보드 조회
        Board board = findBoard(id);

        // 초대된 유저인지 체크
        checkInvitedUser(board, user);

        // 수정
        board.update(boardRequestDto);
        return new BoardResponseDto(board);
    }

    @Transactional
    public ResponseEntity<ApiResponseDto> deleteBoard(Long id, User user) {
        // 삭제할 보드 조회
        Board board = findBoard(id);

        // 초대된 유저인지 체크
        checkInvitedUser(board, user);

        // db 저장
        boardRepository.delete(board);
        return ResponseEntity.ok().body(new ApiResponseDto("보드 삭제 완료!", HttpStatus.OK.value()));
    }


    @Transactional
    public ResponseEntity<ApiResponseDto> inviteBoard(UserBoardRequestDto userBoardRequestDto, User user) {
        // 초대 유저 조회
        User targeUser = findUser(user, userBoardRequestDto.getUsername());

        // 초대할 보드 조회
        Board tagetBoard = findBoard(userBoardRequestDto.getBoardId());

        // 유저보드와 관계 매핑
        UserBoard userBoard = new UserBoard(targeUser, tagetBoard);

        // db 저장
        userBoardRepository.save(userBoard);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 초대 완료!", HttpStatus.OK.value()));
    }


    // ===================== 공통 메서드 ===================== //

    // 보드 조회
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
    }

    // 유저 조회
    public User findUser(User user, String username) {
        if (user.getUsername().equals(username)) {
            throw new IllegalArgumentException("본인은 초대할 수 없습니다");
        }

        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("초대받지 못한 보드입니다."));
    }

    // 초대 유저 검증
    private void checkInvitedUser(Board board, User user) {

        // 조회한 보드에서 유저보드 리스트 가져오기
        List<UserBoard> userBoards = board.getUserBoards();

        // 유저보드에서 유저이름을 추출하고, 현재 인가된 사용자의 이름과 비교
        for (UserBoard userBoard : userBoards) {
            if (!userBoard.getUser().getUsername().equals(user.getUsername())) {
                throw new IllegalArgumentException("초대된 유저가 아닙니다.");
            }
        }
    }

}
