package com.passion.teampassiontrelloproject.user.entity;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.common.blacklist.entity.UserBlackList;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.change.password.PasswordManager;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    private boolean isBlocked = false;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Board> boards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Columns> columns;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Card> cards;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private UserBlackList uerBlackList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserBoard> userBoards;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<PasswordManager> passwordManagers;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void switchBlock() {
        this.isBlocked = !this.isBlocked;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
