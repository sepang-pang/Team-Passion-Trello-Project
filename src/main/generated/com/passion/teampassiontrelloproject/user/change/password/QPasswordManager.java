package com.passion.teampassiontrelloproject.user.change.password;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPasswordManager is a Querydsl query type for PasswordManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPasswordManager extends EntityPathBase<PasswordManager> {

    private static final long serialVersionUID = -806947347L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPasswordManager passwordManager = new QPasswordManager("passwordManager");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public QPasswordManager(String variable) {
        this(PasswordManager.class, forVariable(variable), INITS);
    }

    public QPasswordManager(Path<? extends PasswordManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPasswordManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPasswordManager(PathMetadata metadata, PathInits inits) {
        this(PasswordManager.class, metadata, inits);
    }

    public QPasswordManager(Class<? extends PasswordManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

