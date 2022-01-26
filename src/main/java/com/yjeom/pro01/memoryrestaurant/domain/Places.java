package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Table(name="places")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String placeName;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(nullable = false)
    private double positionX;

    @Column(nullable = false)
    private double positionY;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Places(String placeName,String content,double positionX,double positionY,Member member){
        this.placeName=placeName;
        this.content=content;
        this.positionX=positionX;
        this.positionY=positionY;
        this.member=member;
    }

    public void update(String content){
        this.content=content;
    }

}
