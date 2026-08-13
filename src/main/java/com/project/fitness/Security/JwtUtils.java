package com.project.fitness.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtils {

    private final String secret = "YS1zdHJpbmctc2VjcmV0LWF0LWZhaXphbi1maXRuZXNzLXRyYWNraW5nMjAyNi1iaXRzLWxvbmc=";
    private final int expiryInMs = 86400000;

    public String getUserIdFromHeader(String jwtToken){
        return Jwts.parser().verifyWith((SecretKey) keys())
                .build().parseSignedClaims(jwtToken)
                .getPayload().getSubject();
    }

    public String generateJwtWithId(String userId, String role){
            return Jwts.builder()
                    .subject(userId)
                    .claim("roles", List.of(role))
                    .issuedAt(new Date())
                    .expiration(new Date(new Date().getTime() + expiryInMs))
                    .signWith(keys())
                    .compact();
    }

    public Boolean validate(String token){
        try {
            Jwts.parser().verifyWith((SecretKey) keys())
                    .build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Key keys(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }


    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader("authorization");

        if(bearerToken != null && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

    public Claims getAllClaims(String jwt) {
        return Jwts.parser().verifyWith((SecretKey) keys())
                .build().parseSignedClaims(jwt)
                .getPayload();
    }
}
