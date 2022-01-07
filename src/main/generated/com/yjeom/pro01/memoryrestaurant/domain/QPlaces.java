package com.yjeom.pro01.memoryrestaurant.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPlaces is a Querydsl query type for Places
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPlaces extends EntityPathBase<Places> {

    private static final long serialVersionUID = -1179532017L;

    public static final QPlaces places = new QPlaces("places");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath place_name = createString("place_name");

    public final NumberPath<Double> position_x = createNumber("position_x", Double.class);

    public final NumberPath<Double> position_y = createNumber("position_y", Double.class);

    public QPlaces(String variable) {
        super(Places.class, forVariable(variable));
    }

    public QPlaces(Path<? extends Places> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPlaces(PathMetadata metadata) {
        super(Places.class, metadata);
    }

}

