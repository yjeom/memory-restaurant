package com.yjeom.pro01.memoryrestaurant.controller;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.dto.MemberFormDto;
import com.yjeom.pro01.memoryrestaurant.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/member")
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            if(bindingResult.hasFieldErrors("name")){
                model.addAttribute("nameError",bindingResult.getFieldError("name").getDefaultMessage());
            }
            if(bindingResult.hasFieldErrors("email")){
                model.addAttribute("emailError",bindingResult.getFieldError("email").getDefaultMessage());
            }
            if(bindingResult.hasFieldErrors("password")){
                model.addAttribute("passwordError",bindingResult.getFieldError("password").getDefaultMessage());
            }
            return "member/memberForm";
        }
        try{
            String password=passwordEncoder.encode(memberFormDto.getPassword());
            Member member=Member.builder()
                    .name(memberFormDto.getName())
                    .email(memberFormDto.getEmail())
                    .password(password)
                    .build();
            memberService.createMember(member);
        }catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }
}
