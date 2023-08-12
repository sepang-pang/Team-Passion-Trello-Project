package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.passion.teampassiontrelloproject.user.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<User> findByUsernameAndIsDeletedFalse(String username) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(user)
                                .where(
                                        user.username.eq(username),
                                        user.isBlocked.eq(false)
                                )
                                .fetchOne()
                );
    }


    @Override
    public Optional<User> findByUserIdAndIsDeletedFalse(Long id) {
        return
                Optional.ofNullable(
                        jpaQueryFactory
                                .selectFrom(user)
                                .where(
                                        user.id.eq(id),
                                        user.isBlocked.eq(false)
                                )
                                .fetchOne()
                );
    }

//    @Override
//    public User fetchAllChildrenWithUser(Long userId) {
//        return jpaQueryFactory
//                .select(user)
//                .from(user)
//                .leftJoin(user.boards, board)
//                .leftJoin(user.columns, columns)
//                .leftJoin(user.cards, card)
//                .leftJoin(user.userBoards, userBoard)
//                .leftJoin(user.uerBlackList, userBlackList)
//                .leftJoin(user.passwordManagers, passwordManager)
//                .fetchJoin()
//                .where(user.id.eq(userId))
//                .fetchOne();
//    }
}
