package com.passion.teampassiontrelloproject.column.service;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.service.BoardService;
import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.dto.ColumnsResponseDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.repository.ColumnsRepository;
import com.passion.teampassiontrelloproject.common.advice.custom.BoardInvitationNotFoundException;
import com.passion.teampassiontrelloproject.common.advice.custom.BoardNotFoundException;
import com.passion.teampassiontrelloproject.common.slacknotify.SlackService;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.repository.UserBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnsServiceImpl implements ColumnsService {
    private final BoardService boardService;
    private final ColumnsRepository columnsRepository;
    private final UserBoardRepository userBoardRepository;
    private final SlackService slackService;

    @Transactional
    @Override
    public ColumnsResponseDto getColumnsById(Long id) {
        Columns columns = findColumns(id);
        return new ColumnsResponseDto(columns);
    }

    @Transactional
    @Override
    public ColumnsResponseDto createColumns(ColumnsRequestDto requestDto, Long boardId, User user) {
        authority(user, boardId);
        Board board = boardService.findBoard(requestDto.getBoardId());
        Columns columns = new Columns(requestDto.getTitle());
        columns.setUser(user);
        columns.setBoard(board);
        columns.setDescription(requestDto.getDescription());
        columns.setName(requestDto.getName());

        var savedColumns = columnsRepository.save(columns);

        // 슬랙 메시지 전송
        String message = "새로운 컬럼이 생성되었습니다: " + columns.getTitle();
        slackService.sendSlackNotification(message);

        return new ColumnsResponseDto(savedColumns);
    }

    @Transactional
    @Override
    public void deleteColumns(Columns columns, User user) {
        columnsRepository.delete(columns);
    }

    @Transactional
    @Override
    public ColumnsResponseDto updateColumns(ColumnsRequestDto requestDto,Columns columns,  User user) {
        columns.setTitle(requestDto.getTitle());
        columns.setName(requestDto.getName());
        columns.setDescription(requestDto.getDescription());

        // 슬랙 메시지 전송
        String message = "컬럼이 수정되었습니다: " + columns.getTitle();
        slackService.sendSlackNotification(message);

        return new ColumnsResponseDto(columns);
    }

    @Transactional
    @Override
    public Columns findColumns(long id) {
        return columnsRepository.findById(id).orElseThrow(() ->
                new BoardNotFoundException("선택한 columns는 존재하지 않습니다.")
        );
    }

    // 보드에 속한 유저가 아니라면 카드에 대한 권한을 가질 수 없음
    public void authority(User user, Long boardId) {
        userBoardRepository.findByUserIdAndBoardId(user.getId(), boardId).orElseThrow(() ->
                new BoardInvitationNotFoundException("보드에 속한 유저가 아닙니다."));
    }

}
