package com.yjeom.pro01.memoryrestaurant.dto;

import com.yjeom.pro01.memoryrestaurant.domain.Places;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlacesSaveRequestDto {
    private String place_name;
    private String content;
    private double position_x;
    private double position_y;

    @Builder
    public PlacesSaveRequestDto(String place_name,String content,double position_x,double position_y){
        this.place_name=place_name;
        this.content=content;
        this.position_x=position_x;
        this.position_y=position_y;
    }

    public Places toEntity(){
        return Places.builder()
                .place_name(place_name)
                .content(content)
                .position_x(position_x)
                .position_y(position_y)
                .build();
    }
}
