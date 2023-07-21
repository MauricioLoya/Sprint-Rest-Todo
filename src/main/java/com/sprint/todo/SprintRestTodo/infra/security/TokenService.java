package com.sprint.todo.SprintRestTodo.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sprint.todo.SprintRestTodo.domain.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.jwt.secret}")
    private String JWT_SECRET_KEY;

    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            return JWT.create()
                    .withIssuer("Sprint Rest Todo")
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException(exception);
        }
    }

    public Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(20).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("Sprint Rest Todo")
                    .build()
                    .verify(token);

            return decodedJWT.getSubject();
        }catch (JWTVerificationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Invalid token");
        }catch (NullPointerException exception){
            throw new RuntimeException("Token is null");
        }
    }
    public Long getUserId(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                    .withIssuer("Sprint Rest Todo")
                    .build()
                    .verify(token);
            return decodedJWT.getClaim("id").asLong();
        }catch (JWTVerificationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new RuntimeException("Invalid token");
        }catch (NullPointerException exception){
            throw new RuntimeException("Token is null");
        }
    }
}
