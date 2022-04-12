package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter @Setter
@Table(name="places")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String placeName;

//    @Column(columnDefinition = "TEXT",nullable = false)
//    private String content;

    @Column(nullable = false)
    private double positionX;

    @Column(nullable = false)
    private double positionY;

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

 public static Place createPlace(String placeName,double positionX,double positionY){
     Place place=new Place();
     place.setPlaceName(placeName);
     place.setPositionX(positionX);
     place.setPositionY(positionY);
     return  place;
 }

//    public void update(String content){
//        this.content=content;
//    }

}
