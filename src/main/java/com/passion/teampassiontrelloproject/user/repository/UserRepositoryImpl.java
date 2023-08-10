package com.passion.teampassiontrelloproject.user.repository;

import com.passion.teampassiontrelloproject.user.change.password.repository.PasswordManagerRepository;
import com.passion.teampassiontrelloproject.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.passion.teampassiontrelloproject.board.entity.QBoard.board;
import static com.passion.teampassiontrelloproject.card.entity.QCard.card;
import static com.passion.teampassiontrelloproject.column.entity.QColumns.columns;
import static com.passion.teampassiontrelloproject.common.blacklist.entity.QUserBlackList.userBlackList;
import static com.passion.teampassiontrelloproject.user.change.password.QPasswordManager.passwordManager;
import static com.passion.teampassiontrelloproject.user.entity.QUser.user;
import static com.passion.teampassiontrelloproject.userBoard.entity.QUserBoard.userBoard;

public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final PasswordManagerRepository passwordManagerRepository;

    public UserRepositoryImpl(EntityManager em, PasswordManagerRepository passwordManagerRepository) {
        this.jpaQueryFactory = new JPAQueryFactory(em);
        this.passwordManagerRepository = passwordManagerRepository;
    }

    public void deleteUserUsingFetchAllChildren(User userId) {
        User userToDelete = fetchAllChildrenWithUser(userId.getId());

        if (userToDelete != null) {
                jpaQueryFactory
                        .delete(user)
                        .where(user.id.eq(userToDelete.getId()))
                        .execute();
        }
    }

    public User fetchAllChildrenWithUser(Long userId) {
        return jpaQueryFactory
                .select(user)
                .from(user)
                .leftJoin(user.boards, board)
                .leftJoin(user.columns, columns)
                .leftJoin(user.cards, card)
                .leftJoin(user.userBoards, userBoard)
                .leftJoin(user.uerBlackList, userBlackList)
                .leftJoin(user.passwordManagers, passwordManager)
                .fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();
    }

}
