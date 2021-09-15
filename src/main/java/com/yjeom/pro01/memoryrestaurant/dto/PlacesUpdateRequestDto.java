package com.yjeom.pro01.memoryrestaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlacesUpdateRequestDto {
    private String content;

    @Builder
    public PlacesUpdateRequestDto(String content){
        this.content=content;
    }
}
