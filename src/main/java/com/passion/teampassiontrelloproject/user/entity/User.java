package com.passion.teampassiontrelloproject.user.entity;

import com.passion.teampassiontrelloproject.common.entity.Timestamped;
import com.passion.teampassiontrelloproject.withdrawn.entity.WithdrawnUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
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

    // 블랙리스트 상태값
    private boolean isBlocked = false;

    // soft-delete 상태값
    private boolean isDeleted = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdrawn_user_id")
    private WithdrawnUser withdrawnUser;

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


    // ============= 회원 탈퇴 관련 메소드 ============= //
    public void performWithdrawal() {
        this.username = randomUsername();
        this.password = randomPassword();
        this.email = randomEmail();
        this.isDeleted = true;
    }
    private String randomUsername() {
        return UUID.randomUUID().toString().substring(0, 11);
    }

    private String randomPassword() { return UUID.randomUUID().toString().substring(0, 17); }

    private String randomEmail() {
        return UUID.randomUUID().toString().substring(0, 11) + "@example.com";
    }

    public void updateWithdrawn(WithdrawnUser withdrawnUser) {
        this.withdrawnUser = withdrawnUser;
    }

    private String encodePassword(String rawPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(rawPassword);
    }
}
