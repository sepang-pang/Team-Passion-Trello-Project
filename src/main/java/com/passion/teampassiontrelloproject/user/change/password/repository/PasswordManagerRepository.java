package com.passion.teampassiontrelloproject.user.change.password.repository;

import com.passion.teampassiontrelloproject.user.change.password.PasswordManager;
import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PasswordManagerRepository extends JpaRepository<PasswordManager, Long> {
    List<PasswordManager> findTop3ByUserOrderByCreatedAtDesc(User user);
}