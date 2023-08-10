package com.passion.teampassiontrelloproject.cardCollaborators.entity;

import com.passion.teampassiontrelloproject.card.entity.Card;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UniqueUserCard", columnNames = {"user_id", "card_id"})
}) // 동일한 유저가 동일한 보드에 두 번 이상 등록되지 않도록 설정
public class CardCollaborators {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="card_id")
    private Card card;


    public CardCollaborators(User collaborator, Card tagetCard) {
        this.user = collaborator;
        this.card = tagetCard;
        tagetCard.getCardCollaborators().add(this);
    }
}
