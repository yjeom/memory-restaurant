package com.yjeom.pro01.memoryrestaurant.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private String token;

    private Long id;

    private String email;

    private String name;

    private String password;

}
