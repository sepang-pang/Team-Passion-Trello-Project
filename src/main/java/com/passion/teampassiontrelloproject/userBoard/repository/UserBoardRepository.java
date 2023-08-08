package com.passion.teampassiontrelloproject.userBoard.repository;

import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard,Long > {
    Optional<UserBoard> findByUser(User user);
}
