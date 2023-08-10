package com.passion.teampassiontrelloproject.card.entity;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.column.entity.Columns;
import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
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
@Table(name = "card")
@NoArgsConstructor
public class Card extends Timestamped {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long card_id;

    private String title;

    private String username;

    private String description;

    private String background_color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columns_id")
    private Columns columns;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CardCollaborators> CardCollaborators = new ArrayList<>();

    public Card(CardRequestDto cardRequestDto, User user, Columns columns) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.background_color = cardRequestDto.getBackground_color();
        this.username = user.getUsername();
        this.user = user;
        this.columns = columns;
        columns.getCardList().add(this);
    }

    public void update(CardRequestDto cardRequestDto) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.background_color = cardRequestDto.getBackground_color();
    }


}
