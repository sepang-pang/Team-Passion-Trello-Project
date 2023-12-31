package com.passion.teampassiontrelloproject.userBoard.entity;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.dto.UserBoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserBoard", columnNames = {"user_id", "board_id"})
}) // 동일한 유저가 동일한 보드에 두 번 이상 등록되지 않도록 설정
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id" )
    private User user;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    public UserBoard(User user, Board board) {
        this.user = user;
        this.board = board;
        board.getUserBoards().add(this);
    }
}
