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

    private Long apiId;

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

    @Builder
 public Place(String placeName,Long apiId,double positionX,double positionY){
    this.placeName=placeName;
    this.apiId=apiId;
    this.positionX=positionX;
    this.positionY=positionY;
 }

//    public void update(String content){
//        this.content=content;
//    }

}
