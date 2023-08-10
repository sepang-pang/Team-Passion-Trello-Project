package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
