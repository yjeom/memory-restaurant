package com.yjeom.pro01.memoryrestaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPlaces is a Querydsl query type for Places
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaces extends EntityPathBase<Places> {

    private static final long serialVersionUID = -1179532017L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPlaces places = new QPlaces("places");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath placeName = createString("placeName");

    public final NumberPath<Double> positionX = createNumber("positionX", Double.class);

    public final NumberPath<Double> positionY = createNumber("positionY", Double.class);

    public QPlaces(String variable) {
        this(Places.class, forVariable(variable), INITS);
    }

    public QPlaces(Path<? extends Places> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPlaces(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPlaces(PathMetadata metadata, PathInits inits) {
        this(Places.class, metadata, inits);
    }

    public QPlaces(Class<? extends Places> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

