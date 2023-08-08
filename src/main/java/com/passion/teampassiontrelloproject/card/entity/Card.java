package com.passion.teampassiontrelloproject.card.entity;

import com.passion.teampassiontrelloproject.card.dto.CardRequestDto;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public Card(CardRequestDto cardRequestDto, User user) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.background_color = cardRequestDto.getBackground_color();
        this.username = user.getUsername();
        this.user = user;

    }

    public void update(CardRequestDto cardRequestDto) {
        this.title = cardRequestDto.getTitle();
        this.description = cardRequestDto.getDescription();
        this.background_color = cardRequestDto.getBackground_color();
    }
}
