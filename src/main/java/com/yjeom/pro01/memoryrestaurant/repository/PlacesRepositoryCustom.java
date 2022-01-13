package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlacesRepositoryCustom {

    List<Places> getBoundPlaceList(double sw_x,double sw_y,
                                   double ne_x,double ne_y);


}
