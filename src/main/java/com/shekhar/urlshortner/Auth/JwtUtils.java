package com.shekhar.urlshortner.Auth;

import com.shekhar.urlshortner.Auth.Dto.LoginDTO;
import com.shekhar.urlshortner.Auth.Models.User;
import com.shekhar.urlshortner.Auth.Repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpirationMills;

    public String generateToken(LoginDTO loginDTO){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, loginDTO.getEmail());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMills))
                .signWith(getSigninKey())
                .compact();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

}
