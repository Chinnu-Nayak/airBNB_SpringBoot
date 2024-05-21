package com.AirBNB14.Service;

import com.AirBNB14.entity.PropertyUser;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.PseudoColumnUsage;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.security.key}")
    private String securityKey;
    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.expiry.time}")
    private int expiryTime;
    private Algorithm algorithm;
    private final static String User_Name="username";
    @PostConstruct
    public void  postConstruct(){
       algorithm= Algorithm.HMAC256(securityKey);
    }
    public String generateToken(PropertyUser propertyUser){
       return JWT.create().withClaim(User_Name,propertyUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
                .withIssuer(issuer)
                .sign(algorithm);
    }
    public String getUsername(String token){
        DecodedJWT verify = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return verify.getClaim(User_Name).asString();

    }
}
