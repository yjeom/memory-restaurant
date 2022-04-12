package com.yjeom.pro01.memoryrestaurant.dto;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlacesSaveRequestDto {
    private String placeName;
    private String content;
    private double positionX;
    private double positionY;
    private Member member;

    @Builder
    public PlacesSaveRequestDto(String placeName,String content,double positionX,double positionY,Member member){
        this.placeName=placeName;
        this.content=content;
        this.positionX=positionX;
        this.positionY=positionY;
        this.member=member;
    }

//    public Place toEntity(){
//        return Place.builder()
//                .placeName(placeName)
//                .positionX(positionX)
//                .positionY(positionY)
//                .build();
//    }
}
