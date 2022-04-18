package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceMemoRepository extends JpaRepository<PlaceMemo,Long> {
    List<PlaceMemo> findByPlaceIdOrderByIdDesc(Long placesId);

}
