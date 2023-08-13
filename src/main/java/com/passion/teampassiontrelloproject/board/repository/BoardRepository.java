package com.passion.teampassiontrelloproject.board.repository;

import com.passion.teampassiontrelloproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    Collection<Board> findByUserId(Long id);
}
