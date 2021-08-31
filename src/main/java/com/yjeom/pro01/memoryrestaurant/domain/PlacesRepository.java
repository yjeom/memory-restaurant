package com.yjeom.pro01.memoryrestaurant.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlacesRepository extends JpaRepository<Places,Long> {

    @Query(value = "SELECT p.* FROM PLACES p " +
            "WHERE p.position_x > :sw_x AND p.position_x < :ne_x " +
            "AND p.position_y > :sw_y and p.position_y < :ne_y " +
            "ORDER BY p.id DESC",nativeQuery = true)
    List<Places> findALLDesc(@Param("sw_x")double sw_x,@Param("sw_y")double sw_y,
                             @Param("ne_x")double ne_x,@Param("ne_y")double ne_y);

    @Query(value = "SELECT p.* FROM PLACES p "
            +"WHERE p.position_x = :position_x "
            +"AND p.position_y= :position_y "
            +"LIMIT 1"
            ,nativeQuery = true)
    Places findByPosition(@Param("position_x")double position_x, @Param("position_y") double position_y);
}
