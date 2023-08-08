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
public class UserBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String inviteUsername;
    private String inviteBoardTitle;

    @ManyToOne
    @JoinColumn(name ="user_id" )
    private User user;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    public UserBoard(User user, Board board, UserBoardRequestDto userBoardRequestDto){
        this.user = user;
        this.board = board;
        this.inviteUsername = userBoardRequestDto.getInviteUsername();
        this.inviteBoardTitle = userBoardRequestDto.getInviteBoardTitle();
    }



}
