package com.passion.teampassiontrelloproject.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1523053210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    public final ListPath<com.passion.teampassiontrelloproject.board.entity.Board, com.passion.teampassiontrelloproject.board.entity.QBoard> boards = this.<com.passion.teampassiontrelloproject.board.entity.Board, com.passion.teampassiontrelloproject.board.entity.QBoard>createList("boards", com.passion.teampassiontrelloproject.board.entity.Board.class, com.passion.teampassiontrelloproject.board.entity.QBoard.class, PathInits.DIRECT2);

    public final ListPath<com.passion.teampassiontrelloproject.card.entity.Card, com.passion.teampassiontrelloproject.card.entity.QCard> cards = this.<com.passion.teampassiontrelloproject.card.entity.Card, com.passion.teampassiontrelloproject.card.entity.QCard>createList("cards", com.passion.teampassiontrelloproject.card.entity.Card.class, com.passion.teampassiontrelloproject.card.entity.QCard.class, PathInits.DIRECT2);

    public final ListPath<com.passion.teampassiontrelloproject.column.entity.Columns, com.passion.teampassiontrelloproject.column.entity.QColumns> columns = this.<com.passion.teampassiontrelloproject.column.entity.Columns, com.passion.teampassiontrelloproject.column.entity.QColumns>createList("columns", com.passion.teampassiontrelloproject.column.entity.Columns.class, com.passion.teampassiontrelloproject.column.entity.QColumns.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBlocked = createBoolean("isBlocked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final ListPath<com.passion.teampassiontrelloproject.user.change.password.PasswordManager, com.passion.teampassiontrelloproject.user.change.password.QPasswordManager> passwordManagers = this.<com.passion.teampassiontrelloproject.user.change.password.PasswordManager, com.passion.teampassiontrelloproject.user.change.password.QPasswordManager>createList("passwordManagers", com.passion.teampassiontrelloproject.user.change.password.PasswordManager.class, com.passion.teampassiontrelloproject.user.change.password.QPasswordManager.class, PathInits.DIRECT2);

    public final EnumPath<UserRoleEnum> role = createEnum("role", UserRoleEnum.class);

    public final com.passion.teampassiontrelloproject.common.blacklist.entity.QUserBlackList uerBlackList;

    public final ListPath<com.passion.teampassiontrelloproject.userBoard.entity.UserBoard, com.passion.teampassiontrelloproject.userBoard.entity.QUserBoard> userBoards = this.<com.passion.teampassiontrelloproject.userBoard.entity.UserBoard, com.passion.teampassiontrelloproject.userBoard.entity.QUserBoard>createList("userBoards", com.passion.teampassiontrelloproject.userBoard.entity.UserBoard.class, com.passion.teampassiontrelloproject.userBoard.entity.QUserBoard.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.uerBlackList = inits.isInitialized("uerBlackList") ? new com.passion.teampassiontrelloproject.common.blacklist.entity.QUserBlackList(forProperty("uerBlackList"), inits.get("uerBlackList")) : null;
    }

}

