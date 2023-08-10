package com.passion.teampassiontrelloproject.column.entity;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "columns")
public class Columns extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "columns", cascade = CascadeType.REMOVE)
    private List<Card> CardList = new ArrayList<>();

    public Columns(String title){
        this.title = title;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
