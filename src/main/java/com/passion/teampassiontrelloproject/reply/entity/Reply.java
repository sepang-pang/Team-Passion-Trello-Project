package com.passion.teampassiontrelloproject.reply.entity;

import com.passion.teampassiontrelloproject.comment.entity.Comment;
import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment2")
public class Reply extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username2;

    @Column(nullable = false)
    private String description2;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUsername2(String username2) {
        this.username2 = username2;
    }
    public void setDescription2(String description2) {
        this.description2 = description2;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public void setUser(User user) {
        this.user = user;
    }
}