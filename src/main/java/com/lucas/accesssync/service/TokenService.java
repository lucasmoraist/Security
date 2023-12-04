package com.lucas.accesssync.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.lucas.accesssync.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String jwt; // esse valor será pego das variaveis de ambiente no momento do deploy

    public String generateToken(User user){

        try{
            /**
             * @param HMAC256 cria Hashs únicas
             */
            Algorithm algorithm = Algorithm.HMAC256(jwt);

            /**
             * @param withIssuer nome de quem criou o token
             * @param withSubject usuário que está recebendo o token
             * @param withExpiresAt tempo de expiração
             * @param sign faz a assintura e geração final
             */
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getUsername())
                    .withExpiresAt(genExpirateDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException e){
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(jwt);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch(JWTVerificationException e){
            return "";
        }
    }

    /**
     * @return nossa data e hora atual, mais 2 horas, e transformou em um Instant que está na nossa zona com horário de brasilia
     */
    private Instant genExpirateDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
