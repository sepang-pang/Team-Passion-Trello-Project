package com.passion.teampassiontrelloproject.common.blacklist.repository;

import com.passion.teampassiontrelloproject.common.blacklist.entity.UserBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBlackListRepository extends JpaRepository<UserBlackList, Long> {
    Optional<UserBlackList> findByUserId(Long id);
    Optional<UserBlackList> findByUsername(String username);
}