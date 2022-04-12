package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "place_memo")
public class PlaceMemo extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "place_memo_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "places_id")
    private Place place;

    @ManyToOne
    @JoinColumn(name = "memo_img_id")
    private MemoImg memoImg;

    private double rating;

    private String content;

    public static PlaceMemo createPlaceMemo(Place place,MemoImg memoImg,double rating,String content){
        PlaceMemo placeMemo=new PlaceMemo();
        placeMemo.setPlace(place);
        placeMemo.setMemoImg(memoImg);
        placeMemo.setRating(rating);
        placeMemo.setContent(content);
        return placeMemo;
    }
}
