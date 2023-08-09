package com.passion.teampassiontrelloproject.board.repository;

import com.passion.teampassiontrelloproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {

    Optional<Board> findByTitle(String title);

}
