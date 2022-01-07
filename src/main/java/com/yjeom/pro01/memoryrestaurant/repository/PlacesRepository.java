package com.yjeom.pro01.memoryrestaurant.repository;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places,Long> , PlacesRepositoryCustom{

    @Query(value = "SELECT p.* FROM PLACES p "
            +"WHERE p.position_x = :position_x "
            +"AND p.position_y= :position_y "
            +"LIMIT 1"
            ,nativeQuery = true)
    Places findByPosition(@Param("position_x")double position_x, @Param("position_y") double position_y);
}
