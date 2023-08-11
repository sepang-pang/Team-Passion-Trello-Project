package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.board.entity.Board;
import com.passion.teampassiontrelloproject.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    public Optional<User> findByUsernameAndIsDeletedFalse(String username);
    public Optional<User> findByUsernameAndIsDeletedTrue(String username);
    public Optional<User> findByUserIdAndIsDeletedFalse(Long id);

}
