package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
