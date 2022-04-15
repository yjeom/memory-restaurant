package com.yjeom.pro01.memoryrestaurant.dto;

import lombok.*;

import java.util.List;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto <T>{
    private String error;

    private List<T> data;
}
