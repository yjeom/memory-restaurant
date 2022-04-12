package com.yjeom.pro01.memoryrestaurant.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Table(name="memo_img")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MemoImg extends BaseTime{

    @Id
    @Column(name = "memo_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    //todo: memo 지금 places 와 연결

    public void updateMemoImg(String oriImgName,String imgName,String imgUrl){
        this.oriImgName=oriImgName;
        this.imgName=imgName;
        this.imgUrl=imgUrl;
    }
}
