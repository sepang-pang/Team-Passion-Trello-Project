package com.passion.teampassiontrelloproject.user.service;

import com.passion.teampassiontrelloproject.user.entity.User;
import com.passion.teampassiontrelloproject.user.entity.UserRoleEnum;
import com.passion.teampassiontrelloproject.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
@Commit
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @BeforeEach
    void setUp() {

    }

    @Test
    void testCreateDummyUser() {
        for (int i = 0; i < 5; i++) {
            String username = "tpckd053" + i;
            String password = passwordEncoder.encode("dbsehdwn12!");
            String email = "tpckd053" + i + "@naver.com";
            UserRoleEnum role = UserRoleEnum.USER;
            User user = new User(username, password, email, role);
            userRepository.save(user);
        }
    }

    @Test
    void testIsDeletedUser() {
        Optional<User> user = userRepository.findByUsernameAndIsDeletedFalse("tpckd0530");
        //false 면 통과x true 면 통과
        if (user.isPresent()) {
            System.out.println("통과!");
        }
        else {
            System.out.println("통과x");
        }
    }

}