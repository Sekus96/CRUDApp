package com.example.CRUDApp.security;

import com.example.CRUDApp.entities.Role;
import com.example.CRUDApp.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTGenerator {


    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication) {
        UserEntity userEntity = customUserDetailsService.findByUsername(authentication.getName());

        Hibernate.initialize(userEntity.getRoles());

        Date currentDate = new Date();
        String roles = userEntity.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setHeaderParam("alg", "HS512")
                .setHeaderParam("typ", "JWT")
                .setSubject(userEntity.getUsername())
                .claim("roles", roles)
                .claim("name", userEntity.getUsername())
                .setIssuedAt(currentDate)
                .setExpiration(new Date(currentDate.getTime() + SecurityConstants.JWT_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken (String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e){
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect");
        }
    }

    public Key getKey() {
        return key;
    }
}
