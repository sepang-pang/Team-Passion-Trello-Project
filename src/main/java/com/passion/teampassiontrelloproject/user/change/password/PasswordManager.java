package com.passion.teampassiontrelloproject.user.change.password;

import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "password_manager")
public class PasswordManager extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public PasswordManager(String password, User user) {
        this.password = password;
        this.user = user;
    }
}
