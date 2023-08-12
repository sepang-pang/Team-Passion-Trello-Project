package com.passion.teampassiontrelloproject.comment.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = -808666026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QComment comment = new QComment("comment");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    public final com.passion.teampassiontrelloproject.card.entity.QCard card;

    public final ListPath<com.passion.teampassiontrelloproject.comment2.entity.Comment2, com.passion.teampassiontrelloproject.comment2.entity.QComment2> Comment2List = this.<com.passion.teampassiontrelloproject.comment2.entity.Comment2, com.passion.teampassiontrelloproject.comment2.entity.QComment2>createList("Comment2List", com.passion.teampassiontrelloproject.comment2.entity.Comment2.class, com.passion.teampassiontrelloproject.comment2.entity.QComment2.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public final StringPath username = createString("username");

    public QComment(String variable) {
        this(Comment.class, forVariable(variable), INITS);
    }

    public QComment(Path<? extends Comment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QComment(PathMetadata metadata, PathInits inits) {
        this(Comment.class, metadata, inits);
    }

    public QComment(Class<? extends Comment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.passion.teampassiontrelloproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user")) : null;
    }

}

