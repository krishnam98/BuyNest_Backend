package com.BuyNest.backend.Service;

import com.BuyNest.backend.Model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    public JWTService(){
        try{
            KeyGenerator keyGen= KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk= keyGen.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
        }
        catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    private String secretKey="";
    public String generateToken(Users user) {
        Map<String,Object> claims= new HashMap<>();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000*60*60*24))
                .and()
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey(){
        byte [] keyBytes= Decoders.BASE64.decode(secretKey);
                return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractUsername(String token) {

        return extractClaims(token, Claims::getSubject);
    }



    private <T> T extractClaims(String token, Function<Claims,T> claimResolver) {
        final Claims claims =extraAllClaims(token);

        return claimResolver.apply(claims);

    }

    private Claims extraAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public boolean validateToken(String token, UserDetails userDetails) {
        final String username=extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaims(token,Claims::getExpiration);
    }


}


