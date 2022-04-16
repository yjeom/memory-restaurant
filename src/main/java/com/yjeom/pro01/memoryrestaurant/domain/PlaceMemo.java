package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "place_memo")
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private  Member member;

    private double rating;

    private String content;

    @Builder
    public  PlaceMemo (Place place,MemoImg memoImg,double rating,String content,Member member){
        this.place=place;
        this.memoImg=memoImg;
        this.rating=rating;
        this.content=content;
        this.member=member;
    }
}
