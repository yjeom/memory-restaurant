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
    private String place_name;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String content;

    @Column(nullable = false)
    private double position_x;

    @Column(nullable = false)
    private double position_y;

    @Builder
    public Places(String place_name,String content,double position_x,double position_y){
        this.place_name=place_name;
        this.content=content;
        this.position_x=position_x;
        this.position_y=position_y;
    }

    public void update(String content){
        this.content=content;
    }

}
