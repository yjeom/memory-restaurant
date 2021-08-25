package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Places {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String place_name;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(length = 500,nullable = false)
    private String position_x;

    @Column(length = 500,nullable = false)
    private String position_y;

    @Builder
    public Places(String place_name,String content,String position_x,String position_y){
        this.place_name=place_name;
        this.content=content;
        this.position_x=position_x;
        this.position_y=position_y;
    }
}
