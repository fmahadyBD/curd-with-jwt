package com.example.curd_with_jwt.services.userdetailsservice;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;
@Service
public class JwtService {
    /*
    1.generateToken
    2.generateKey

    3.extractUsername
    4.getClaims
    5.isTokenValid


     */

    private static final  String SECRET="638CBE3A90E0303BF3808F40F95A7F02A24B4B5D029C954CF553F79E9EF1DC0384BE681C249F1223F6B55AA21DC070914834CA22C8DD98E14A872CA010091ACC";
    private static final long VALIDITY= TimeUnit.MINUTES.toMillis(1000000);

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .signWith(generateKey())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
                .compact();
    }

    private SecretKey generateKey(){
        byte [] decodedKey = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(decodedKey);
    }


    //2nd part

    public String extractUsername(String token){
        Claims claims = getClaims(token);
        return claims.getSubject();


    }
    public Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token){
        Claims claims = getClaims(token);
        return  claims.getExpiration().after(Date.from(Instant.now()));
    }

}