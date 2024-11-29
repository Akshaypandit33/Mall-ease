package com.akshay.backend.SecurityConfig;

import com.akshay.backend.Model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

public class JwtProvider {
    private static SecretKey key= Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateToken(Authentication authentication)
    {
        String jwt=Jwts.builder()
                .issuer(JwtConstants.Issuer)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+8640000))
                .claim("email",authentication.getName())
                .claim("role",authentication.getAuthorities())
                .signWith(key)
                .compact();
        return jwt;
    }

    public static Claims extractClaims(String token)
    {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //    extract email
    public static String extractEmail(String jwt){
        jwt=jwt.substring(7);
        Claims claims=extractClaims(jwt);

        return String.valueOf(claims.get("email"));
    }
    //    extract roles
    public static String extractRole(String jwt)
    {
        Claims claims=extractClaims(jwt.substring(7));
        return (String.valueOf(claims.get("role")));
    }
}
