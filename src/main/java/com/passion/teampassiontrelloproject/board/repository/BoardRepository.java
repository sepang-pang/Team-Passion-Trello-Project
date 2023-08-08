package com.passion.teampassiontrelloproject.board.repository;

import com.passion.teampassiontrelloproject.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {


}
