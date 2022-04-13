package com.yjeom.pro01.memoryrestaurant.dto;

import com.yjeom.pro01.memoryrestaurant.domain.MemoImg;
import com.yjeom.pro01.memoryrestaurant.domain.Place;
import com.yjeom.pro01.memoryrestaurant.domain.PlaceMemo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Getter @Setter
@NoArgsConstructor
public class PlaceMemoDto {

    private Long placeMemoId;

    private String regDate;

    private String imgUrl;

    private double rating;

    private String content;

    private String placeName;

    private Long placeApiId;

    private double positionX;

    private double positionY;

    @Builder
    public PlaceMemoDto(PlaceMemo placeMemo, Place place, MemoImg memoImg){
        this.placeMemoId=placeMemo.getId();
        this.regDate=placeMemo.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH;dd"));
        this.imgUrl=memoImg.getImgUrl();
        this.rating=placeMemo.getRating();
        this.content=placeMemo.getContent();
        this.placeName=place.getPlaceName();
        this.placeApiId=place.getApiId();
        this.positionX=place.getPositionX();
        this.positionY=place.getPositionY();

    }
}
