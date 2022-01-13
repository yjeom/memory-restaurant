package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places,Long> , PlacesRepositoryCustom{

    @Query(value = "SELECT p.* FROM PLACES p "
            +"WHERE p.positionX = :positionX "
            +"AND p.positionY= :positionY "
            +"LIMIT 1"
            ,nativeQuery = true)
    Places findByPosition(@Param("positionX")double positionX, @Param("positionY") double positionY);

    Page<Places> findByPositionXAndPositionYOrderByIdDesc(double positionX, double positionY, Pageable pageable);
}
