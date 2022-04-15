package com.yjeom.pro01.memoryrestaurant.security;

import com.yjeom.pro01.memoryrestaurant.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {

    @Value("${TOKEN_SECRET_KEY}")
    String secretKey;

    public String create(Member member){
        Date expiryDate=Date.from(
                Instant.now().plus(1, ChronoUnit.DAYS)
        );
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512,secretKey)
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validate(String token){
        Claims claims=Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
