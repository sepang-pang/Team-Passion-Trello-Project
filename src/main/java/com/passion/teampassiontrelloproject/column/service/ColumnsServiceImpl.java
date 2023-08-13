package com.passion.teampassiontrelloproject.column.service;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.service.BoardService;
import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.dto.ColumnsResponseDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.repository.ColumnsRepository;
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

        return new ColumnsResponseDto(savedColumns);
    }

    @Transactional
    @Override
    public void deleteColumns(Columns columns, User user) {
        columnsRepository.delete(columns);
    }

    @Transactional
    @Override

    public ColumnsResponseDto updateColumns(Columns columns, ColumnsRequestDto requestDto, User user) {

        columns.setTitle(requestDto.getTitle());
        columns.setName(requestDto.getName());
        columns.setDescription(requestDto.getDescription());

        return new ColumnsResponseDto(columns);
    }

    @Transactional
    @Override
    public Columns findColumns(long id) {
        return columnsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 board는 존재하지 않습니다.")
        );
    }

    // 보드에 속한 유저가 아니라면 카드에 대한 권한을 가질 수 없음
    public void authority(User user, Long boardId) {
        userBoardRepository.findByUserIdAndBoardId(user.getId(), boardId).orElseThrow(() ->
                new IllegalArgumentException("보드에 속한 유저가 아닙니다."));
    }

}
