package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.entity.User;

import java.util.Optional;

public interface UserRepositoryCustom {

    public Optional<User> findByUsernameIsDeletedFalse(String username);
//    public User fetchAllChildrenWithUser(Long userId);
}
