package com.passion.teampassiontrelloproject.comment.repository;

import com.passion.teampassiontrelloproject.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
