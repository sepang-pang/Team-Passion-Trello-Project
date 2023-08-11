package com.passion.teampassiontrelloproject.withdrawn.service;

import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.withdrawn.entity.WithdrawnUser;
import com.passion.teampassiontrelloproject.withdrawn.repository.WithdrawnUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class WithdrawnUserServiceImpl implements WithdrawnUserService{

    private final WithdrawnUserRepository withdrawnUserRepository;

    @Override
    @Transactional
    public void backUpUserDate(User user) {
        WithdrawnUser withdrawnUser = new WithdrawnUser(user);
        withdrawnUserRepository.save(withdrawnUser);
    }
}
