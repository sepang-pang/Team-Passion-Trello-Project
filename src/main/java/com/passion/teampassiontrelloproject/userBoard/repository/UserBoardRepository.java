package com.passion.teampassiontrelloproject.userBoard.repository;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.userBoard.entity.UserBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
    Optional<UserBoard> findByUserIdAndBoardId(Long id, Long boardId);
}
