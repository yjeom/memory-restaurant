package com.yjeom.pro01.memoryrestaurant.controller;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.dto.MemberDto;
import com.yjeom.pro01.memoryrestaurant.dto.MemberFormDto;
import com.yjeom.pro01.memoryrestaurant.dto.ResponseDto;
import com.yjeom.pro01.memoryrestaurant.security.TokenProvider;
import com.yjeom.pro01.memoryrestaurant.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    @Autowired
    private TokenProvider tokenProvider;
//    @GetMapping(value = "/new")
//    public String memberForm(Model model){
//        model.addAttribute("memberFormDto",new MemberFormDto());
//        return "member/memberForm";
//    }

    @PostMapping(value = "/auth/signUp")
    public ResponseEntity<?> saveMember(@RequestBody MemberDto memberDto){
        try{
            Member member= Member.builder()
                    .email(memberDto.getEmail())
                    .name(memberDto.getName())
                    .password(passwordEncoder.encode(memberDto.getPassword()))
                    .build();
            Member savedMember=memberService.createMember(member);
            MemberDto responseMemberDto=MemberDto.builder()
                    .email(savedMember.getEmail())
                    .id(savedMember.getId())
                    .name(savedMember.getName())
                    .build();
            return ResponseEntity.ok().body(responseMemberDto);
        }catch (Exception e){
            ResponseDto responseDto=ResponseDto.builder()
                    .error(e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody MemberDto memberDto){
        Member member=memberService.validateExistsMember(
                memberDto.getEmail(), memberDto.getPassword(),passwordEncoder
        );
        if(member !=null){
            String token=tokenProvider.create(member);
            MemberDto responseMemberDto=MemberDto.builder()
                    .email(member.getEmail())
                    .id(member.getId())
                    .name(member.getName())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseMemberDto);
        }else{
            ResponseDto responseDto=ResponseDto.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity.badRequest().body(responseDto);
        }

    }

//
//    @GetMapping(value = "/login")
//    public String login(){
//        return "member/memberLoginForm";
//    }

//    @GetMapping(value = "/login/error")
//    public String loginError(Model model){
//        model.addAttribute("error","아이디 또는 비밀번호를 확인해주세요.");
//        return "member/memberLoginForm";
//    }

}
