package com.yjeom.pro01.memoryrestaurant.dto;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
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

    @Builder
    public PlacesSaveRequestDto(String placeName,String content,double positionX,double positionY){
        this.placeName=placeName;
        this.content=content;
        this.positionX=positionX;
        this.positionY=positionY;
    }

    public Places toEntity(){
        return Places.builder()
                .placeName(placeName)
                .content(content)
                .positionX(positionX)
                .positionY(positionY)
                .build();
    }
}
