package com.passion.teampassiontrelloproject.board.entity;

import com.passion.teampassiontrelloproject.board.dto.BoardRequestDto;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Board(BoardRequestDto requestDto, User user) {
        this.name = user.getUsername();
        this.description = requestDto.getDescription();
        this.backgroundColor = requestDto.getBackgroundColor();
        this.title = requestDto.getTitle();
    }

}
