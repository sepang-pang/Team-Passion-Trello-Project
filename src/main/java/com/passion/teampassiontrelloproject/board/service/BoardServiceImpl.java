package com.passion.teampassiontrelloproject.board.service;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.board.dto.BoardResponseDto;
import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.repository.BoardRepository;
import com.passion.teampassiontrelloproject.common.advice.custom.BoardInvitationNotFoundException;
import com.passion.teampassiontrelloproject.common.advice.custom.BoardNotFoundException;
import com.passion.teampassiontrelloproject.common.advice.custom.SelfInviteNotAllowedException;
import com.passion.teampassiontrelloproject.common.dto.ApiResponseDto;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardRequestDto;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardResponseDto;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final UserBoardRepository userBoardRepository;
    private final UserRepository userRepository;
    private final SlackService slackService;

    // 보드 생성
    @Override
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        // 보드 내용 삽입
        Board board = new Board(boardRequestDto, user);

        // 유저 보드 관계 매핑
        new UserBoard(user, board);

        // 슬랙 메시지 전송
        String message = "새로운 보드가 생성되었습니다: " + board.getTitle();
        slackService.sendSlackNotification(message);

        // db 저장
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    // 보드 조회
    @Override
    public List<BoardResponseDto> getBoard(User user) {
       return boardRepository.findByUserId(user.getId())
               .stream().map(BoardResponseDto::new).toList();
    }

    // 특정 보드 조회
    @Override
    public ResponseEntity<ApiResponseDto> getIdBoard(Long id, User user) {
        List<BoardResponseDto> getBoards = getBoard(user);
        for(BoardResponseDto getBoard : getBoards){
            if(getBoard.getId().equals(id)){
                return ResponseEntity.ok().body(new BoardResponseDto(findBoard(id)));
            }
        }
        return ResponseEntity.badRequest().body(new ApiResponseDto("초대받지 못한 보드입니다.",HttpStatus.BAD_REQUEST.value()));
    }

    // 보드 수정
    @Override
    @Transactional
    public BoardResponseDto updateBoard(Long id, BoardRequestDto boardRequestDto, User user) {
        // 수정할 보드 조회
        Board board = findBoard(id);

        // 수정
        board.update(boardRequestDto);

        // 슬랙 메시지 전송
        String message = "보드가 수정되었습니다: " + board.getTitle();
        slackService.sendSlackNotification(message);

        return new BoardResponseDto(board);
    }

    // 보드 삭제
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> deleteBoard(Long id, User user) {
        // 삭제할 보드 조회
        Board board = findBoard(id);

        // 삭제
        boardRepository.delete(board);

        // 슬랙 메시지
        String message = "보드가 삭제되었습니다: " + board.getTitle();
        slackService.sendSlackNotification(message);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 삭제 완료!", HttpStatus.OK.value()));
    }


    // 보드에 유저 초대
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> inviteBoard(UserBoardRequestDto userBoardRequestDto, User user) {
        // 초대 유저 조회
        User targeUser = findUser(user, userBoardRequestDto.getUsername());

        // 초대할 보드 조회
        Board tagetBoard = findBoard(userBoardRequestDto.getBoardId());

        // 유저보드와 관계 매핑
        UserBoard userBoard = new UserBoard(targeUser, tagetBoard);

        // 보드 생성자(팀장)만 유저 초대
        if(!user.getId().equals(tagetBoard.getUser().getId())){
            throw new AccessDeniedException("초대 권한이 없습니다.");
        }

        // 슬랙 메시지
        String message = targeUser.getUsername() + " 님이 초대되었습니다";
        slackService.sendSlackNotification(message);

        // db 저장
        userBoardRepository.save(userBoard);

        return ResponseEntity.ok().body(new ApiResponseDto("보드 초대 완료!", HttpStatus.OK.value()));
    }

    // 초대된 유저 조회
    @Override
    public List<UserBoardResponseDto> getInviteUser(Long id) {
        // 보드 조회
        Board board = findBoard(id);

        return board.getUserBoards().stream()
                .map(userBoard -> new UserBoardResponseDto(userBoard.getUser().getUsername()))
                .toList();
    }

    // 초대된 유저 제외
    @Override
    @Transactional
    public ResponseEntity<ApiResponseDto> exceptUserBoard(Long BoardId, Long UserId, User user){
        Board board = findBoard(BoardId);
        User targetUser = userRepository.findByUserIdAndIsDeletedFalse(UserId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if(!user.getId().equals(board.getUser().getId())){
            throw new AccessDeniedException("유저 관리 권한이 없습니다.");
        }

        if(targetUser.getId().equals(board.getUser().getId())){
            throw new AccessDeniedException("팀장은 삭제할 수 없습니다.");
        }

        Optional<UserBoard> userBoardOptional = userBoardRepository.findByUserIdAndBoardId(targetUser.getId(), board.getId());
        userBoardOptional.ifPresent(userBoardRepository::delete);

        return ResponseEntity.ok().body(new ApiResponseDto("보드에서 제외하였습니다.",HttpStatus.OK.value()));
    }

    // ===================== 공통 메서드 ===================== //

    // 보드 조회
    @Override
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new BoardNotFoundException("존재하지 않는 보드입니다."));
    }

    // 유저 조회
    public User findUser(User user, String username) {
        if (user.getUsername().equals(username)) {
            throw new SelfInviteNotAllowedException("본인은 초대할 수 없습니다");
        }
        return userRepository.findByUsernameAndIsDeletedFalse(username).orElseThrow(() -> new BoardInvitationNotFoundException("초대받지 못한 보드입니다."));
    }

    // 초대 유저 검증
//    @Override
//    public void checkInvitedUser(Board board, User user) {
//
//        // 조회한 보드에서 유저보드 리스트 가져오기
//        List<UserBoard> userBoards = board.getUserBoards();
//
//        // 유저 네임 리스트 생성
//        List<String> usernames = new ArrayList<>();
//
//        // 유저보드에서 유저이름을 추출하고, 유저네임 리스트에 저장
//        for (UserBoard userBoard : userBoards) {
//            usernames.add(userBoard.getUser().getUsername());
//        }
//
//        // 현재 인가된 사용자의 이름과 비교
//        if (!usernames.contains(user.getUsername())) {
//            throw new IllegalArgumentException("초대된 유저가 아닙니다.");
//        }
//    }


}
