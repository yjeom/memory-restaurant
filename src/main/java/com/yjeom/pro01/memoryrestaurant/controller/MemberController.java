package com.yjeom.pro01.memoryrestaurant.controller;

import com.yjeom.pro01.memoryrestaurant.dto.MemberFormDto;
import com.yjeom.pro01.memoryrestaurant.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/member/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }
}
