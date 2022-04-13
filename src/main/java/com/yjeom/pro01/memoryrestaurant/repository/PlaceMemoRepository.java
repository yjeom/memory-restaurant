package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceMemoRepository extends JpaRepository<PlaceMemo,Long> {
    List<PlaceMemo> findByPlaceId(Long placesId);

    @Query("SELECT COUNT(m) FROM PlaceMemo m "+
            "WHERE m.place.id = :placesId")
    Long countPlaceMemo(@Param("placesId")Long placesId);
}
