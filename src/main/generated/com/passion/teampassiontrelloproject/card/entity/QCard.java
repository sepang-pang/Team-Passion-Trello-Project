package com.passion.teampassiontrelloproject.card.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCard is a Querydsl query type for Card
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCard extends EntityPathBase<Card> {

    private static final long serialVersionUID = -459707280L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCard card = new QCard("card");

    public final com.passion.teampassiontrelloproject.common.entity.QTimestamped _super = new com.passion.teampassiontrelloproject.common.entity.QTimestamped(this);

    public final StringPath background_color = createString("background_color");

    public final NumberPath<Long> card_id = createNumber("card_id", Long.class);

    public final ListPath<com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators, com.passion.teampassiontrelloproject.cardCollaborators.entity.QCardCollaborators> CardCollaborators = this.<com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators, com.passion.teampassiontrelloproject.cardCollaborators.entity.QCardCollaborators>createList("CardCollaborators", com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators.class, com.passion.teampassiontrelloproject.cardCollaborators.entity.QCardCollaborators.class, PathInits.DIRECT2);

    public final com.passion.teampassiontrelloproject.column.entity.QColumns columns;

    public final ListPath<com.passion.teampassiontrelloproject.comment.entity.Comment, com.passion.teampassiontrelloproject.comment.entity.QComment> CommentList = this.<com.passion.teampassiontrelloproject.comment.entity.Comment, com.passion.teampassiontrelloproject.comment.entity.QComment>createList("CommentList", com.passion.teampassiontrelloproject.comment.entity.Comment.class, com.passion.teampassiontrelloproject.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath title = createString("title");

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public final StringPath username = createString("username");

    public QCard(String variable) {
        this(Card.class, forVariable(variable), INITS);
    }

    public QCard(Path<? extends Card> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCard(PathMetadata metadata, PathInits inits) {
        this(Card.class, metadata, inits);
    }

    public QCard(Class<? extends Card> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.columns = inits.isInitialized("columns") ? new com.passion.teampassiontrelloproject.column.entity.QColumns(forProperty("columns"), inits.get("columns")) : null;
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user")) : null;
    }

}

