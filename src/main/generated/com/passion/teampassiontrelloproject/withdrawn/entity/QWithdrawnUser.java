package com.passion.teampassiontrelloproject.withdrawn.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWithdrawnUser is a Querydsl query type for WithdrawnUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWithdrawnUser extends EntityPathBase<WithdrawnUser> {

    private static final long serialVersionUID = -458239647L;

    public static final QWithdrawnUser withdrawnUser = new QWithdrawnUser("withdrawnUser");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isBlocked = createBoolean("isBlocked");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final EnumPath<com.passion.teampassiontrelloproject.user.entity.UserRoleEnum> role = createEnum("role", com.passion.teampassiontrelloproject.user.entity.UserRoleEnum.class);

    public final StringPath username = createString("username");

    public QWithdrawnUser(String variable) {
        super(WithdrawnUser.class, forVariable(variable));
    }

    public QWithdrawnUser(Path<? extends WithdrawnUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWithdrawnUser(PathMetadata metadata) {
        super(WithdrawnUser.class, metadata);
    }

}

