package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryCustom {

    public User fetchAllChildrenWithUser(Long userId);
    public void deleteUserUsingFetchAllChildren(User userId);

}
