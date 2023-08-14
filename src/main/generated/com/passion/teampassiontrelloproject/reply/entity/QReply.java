package com.passion.teampassiontrelloproject.reply.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReply is a Querydsl query type for Reply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReply extends EntityPathBase<Reply> {

    private static final long serialVersionUID = -1784035146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReply reply = new QReply("reply");

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

    public QReply(String variable) {
        this(Reply.class, forVariable(variable), INITS);
    }

    public QReply(Path<? extends Reply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReply(PathMetadata metadata, PathInits inits) {
        this(Reply.class, metadata, inits);
    }

    public QReply(Class<? extends Reply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new com.passion.teampassiontrelloproject.comment.entity.QComment(forProperty("comment"), inits.get("comment")) : null;
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

