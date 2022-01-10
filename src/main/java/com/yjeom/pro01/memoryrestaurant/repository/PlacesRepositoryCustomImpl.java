package com.yjeom.pro01.memoryrestaurant.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjeom.pro01.memoryrestaurant.domain.Places;
import com.yjeom.pro01.memoryrestaurant.domain.QPlaces;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class PlacesRepositoryCustomImpl implements PlacesRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public PlacesRepositoryCustomImpl(EntityManager em){
        queryFactory=new JPAQueryFactory(em);
    }
    @Override
    public List<Places> getBoundPlaceList(double sw_x, double sw_y, double ne_x, double ne_y) {
        QPlaces places= QPlaces.places;
        List<Places> content=queryFactory.selectFrom(places)
                .where(places.positionX.between(sw_x,ne_x)
                        ,places.positionY.between(sw_y,ne_y))
                .orderBy(places.id.desc())
                .fetch();

        return content;
    }

}
