package com.passion.teampassiontrelloproject.board.entity;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Board")
@NoArgsConstructor
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String backgroundColor;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserBoard> UserBoards = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Columns> ColumnsList = new ArrayList<>();

    public Board(BoardRequestDto requestDto, User user) {
        this.name = user.getUsername();
        this.description = requestDto.getDescription();
        this.backgroundColor = requestDto.getBackgroundColor();
        this.title = requestDto.getTitle();
        this.user = user;
    }

    public void update(BoardRequestDto boardRequestDto) {
        this.backgroundColor = boardRequestDto.getBackgroundColor();
        this.description = boardRequestDto.getDescription();
        this.title = boardRequestDto.getTitle();
    }
}
