package com.passion.teampassiontrelloproject.cardCollaborators.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCardCollaborators is a Querydsl query type for CardCollaborators
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCardCollaborators extends EntityPathBase<CardCollaborators> {

    private static final long serialVersionUID = 1550568854L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCardCollaborators cardCollaborators = new QCardCollaborators("cardCollaborators");

    public final com.passion.teampassiontrelloproject.card.entity.QCard card;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.passion.teampassiontrelloproject.user.entity.QUser user;

    public QCardCollaborators(String variable) {
        this(CardCollaborators.class, forVariable(variable), INITS);
    }

    public QCardCollaborators(Path<? extends CardCollaborators> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCardCollaborators(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCardCollaborators(PathMetadata metadata, PathInits inits) {
        this(CardCollaborators.class, metadata, inits);
    }

    public QCardCollaborators(Class<? extends CardCollaborators> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.card = inits.isInitialized("card") ? new com.passion.teampassiontrelloproject.card.entity.QCard(forProperty("card"), inits.get("card")) : null;
        this.user = inits.isInitialized("user") ? new com.passion.teampassiontrelloproject.user.entity.QUser(forProperty("user"), inits.get("user")) : null;
    }

}

