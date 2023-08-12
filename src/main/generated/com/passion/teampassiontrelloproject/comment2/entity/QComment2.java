package com.passion.teampassiontrelloproject.comment2.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment2 is a Querydsl query type for Comment2
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment2 extends EntityPathBase<Comment2> {

    private static final long serialVersionUID = 1642752566L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment2 comment2 = new QComment2("comment2");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    public final com.passion.teampassiontrelloproject.comment.entity.QComment comment;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description2 = createString("description2");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public final StringPath username2 = createString("username2");

    public QComment2(String variable) {
        this(Comment2.class, forVariable(variable), INITS);
    }

    public QComment2(Path<? extends Comment2> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment2(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment2(PathMetadata metadata, PathInits inits) {
        this(Comment2.class, metadata, inits);
    }

    public QComment2(Class<? extends Comment2> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.passion.teampassiontrelloproject.comment.entity.QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user")) : null;
    }

}

