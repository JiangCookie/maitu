package com.ljc.maitu.common.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Map;

/**
 * @author JYH
 * 2019/3/2 13:17
 */
public class JwtUtil {
    private static final String SECRET = "ThisIsASecret";
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    /**
     * 生成Token
     * @param username
     * @return
     */
    public static String generateToken(String username){
        String jwt = Jwts.builder()
                         .setSubject(username)
                         .signWith(key)
                         .compact();
        return jwt;
    }

    public static void validateToken(String token) {
        try {
            // parse the token.
            Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject()
                .equals(SECRET);

        }catch (JwtException e){
            throw new IllegalStateException("Invalid Token. "+e.getMessage());
        }
    }
}
