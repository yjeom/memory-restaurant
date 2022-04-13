package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlaceRepository extends JpaRepository<Place,Long> , PlaceRepositoryCustom {

    Place findByApiId(Long apiId);

    @Query(value = "SELECT p.* FROM PLACES p "
            +"WHERE p.positionX = :positionX "
            +"AND p.positionY= :positionY "
            +"LIMIT 1"
            ,nativeQuery = true)
    Place findByPosition(@Param("positionX")double positionX, @Param("positionY") double positionY);

    Page<Place> findByPositionXAndPositionYOrderByIdDesc(double positionX, double positionY, Pageable pageable);
}
