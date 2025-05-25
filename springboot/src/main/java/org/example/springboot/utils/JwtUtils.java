package org.example.springboot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username, Long userId) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create()
                .withSubject(username)
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
}
