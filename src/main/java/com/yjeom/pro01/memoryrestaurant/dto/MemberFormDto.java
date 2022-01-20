package com.yjeom.pro01.memoryrestaurant.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "필수 입력 값입니다.")
    @Length(min = 4,max = 16,message = "비밀번호는 4자이상, 16자 이하로 입력해주세요")
    private String password;
}
