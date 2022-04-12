package com.yjeom.pro01.memoryrestaurant.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.domain.QPlace;

import javax.persistence.EntityManager;
import java.util.*;

public class PlaceRepositoryCustomImpl implements PlaceRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public PlaceRepositoryCustomImpl(EntityManager em){
        queryFactory=new JPAQueryFactory(em);
    }
    @Override
    public List<Place> getBoundPlaceList(double sw_x, double sw_y, double ne_x, double ne_y) {
        QPlace places= QPlace.place;
        List<Place> content=queryFactory.selectFrom(places)
                .where(places.positionX.between(sw_x,ne_x)
                        ,places.positionY.between(sw_y,ne_y))
                .orderBy(places.id.desc())
                .fetch();

        return content;
    }

}
