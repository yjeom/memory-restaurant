package com.yjeom.pro01.memoryrestaurant.service;


import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService , AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    public Member createMember(Member member){
        validateDuplicateMember(member);
        return  memberRepository.save(member);
    }

    public void validateDuplicateMember(Member member){
        Member findMember=memberRepository.findByEmail(member.getEmail());
        if(findMember !=null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member=memberRepository.findByEmail(email);
        if(member ==null){

            throw  new UsernameNotFoundException(email);
        }
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles("USER")
                .build();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session=request.getSession();
        Member member=memberRepository.findByEmail(authentication.getName());
        session.setAttribute("user",member);
        response.sendRedirect("/");

    }
}
