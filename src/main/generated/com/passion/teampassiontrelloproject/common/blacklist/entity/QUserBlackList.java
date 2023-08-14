package com.passion.teampassiontrelloproject.common.blacklist.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserBlackList is a Querydsl query type for UserBlackList
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserBlackList extends EntityPathBase<UserBlackList> {

    private static final long serialVersionUID = -1520392888L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserBlackList userBlackList = new QUserBlackList("userBlackList");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public final StringPath username = createString("username");

    public QUserBlackList(String variable) {
        this(UserBlackList.class, forVariable(variable), INITS);
    }

    public QUserBlackList(Path<? extends UserBlackList> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserBlackList(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserBlackList(PathMetadata metadata, PathInits inits) {
        this(UserBlackList.class, metadata, inits);
    }

    public QUserBlackList(Class<? extends UserBlackList> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

