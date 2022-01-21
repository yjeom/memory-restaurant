package com.yjeom.pro01.memoryrestaurant.service;


import com.yjeom.pro01.memoryrestaurant.domain.Member;
import com.yjeom.pro01.memoryrestaurant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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
}
