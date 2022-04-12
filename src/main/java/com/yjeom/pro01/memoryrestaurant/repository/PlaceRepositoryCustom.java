package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Place;

import java.util.List;

public interface PlaceRepositoryCustom {

    List<Place> getBoundPlaceList(double sw_x, double sw_y,
                                  double ne_x, double ne_y);


}
