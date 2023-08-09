package com.passion.teampassiontrelloproject.column.service;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.board.repository.BoardRepository;
import com.passion.teampassiontrelloproject.board.service.BoardService;
import com.passion.teampassiontrelloproject.column.dto.ColumnsRequestDto;
import com.passion.teampassiontrelloproject.column.dto.ColumnsResponseDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.repository.ColumnsRepository;
import com.passion.teampassiontrelloproject.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnsServiceImpl implements ColumnsService {
    private final BoardService boardService;
    private final ColumnsRepository columnsRepository;

    @Override
    public ColumnsResponseDto createColumns(ColumnsRequestDto requestDto, User user) {
        Board board = boardService.findBoard(requestDto.getBoardId());
        Columns columns = new Columns(requestDto.getTitle());
        columns.setUser(user);
        columns.setBoard(board);
        columns.setDescription(requestDto.getDescription());
        columns.setName(requestDto.getName());

        var savedColumns = columnsRepository.save(columns);

        return new ColumnsResponseDto(savedColumns);
    }

    @Override
    public void deleteColumns(Columns columns, User user) {
        columnsRepository.delete(columns);
    }

    @Override
    @Transactional
    public ColumnsResponseDto updateColumns(Columns columns, ColumnsRequestDto requestDto, User user) {

        columns.setTitle(requestDto.getTitle());
        columns.setName(requestDto.getName());
        columns.setDescription(requestDto.getDescription());

        return new ColumnsResponseDto(columns);
    }

    @Override
    public Columns findColumns(long id) {
        return columnsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 board는 존재하지 않습니다.")
        );
    }
}
