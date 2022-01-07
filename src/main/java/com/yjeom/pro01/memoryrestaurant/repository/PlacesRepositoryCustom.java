package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Places;

import java.util.List;

public interface PlacesRepositoryCustom {

    List<Places> getBoundPlaceList(double sw_x,double sw_y,
                                   double ne_x,double ne_y);
}
