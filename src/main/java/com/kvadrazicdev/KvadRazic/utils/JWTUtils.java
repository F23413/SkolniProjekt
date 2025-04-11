package com.kvadrazicdev.KvadRazic.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTUtils {
    //Jason Web Token
    private static final long Cas_Expirace = 1000 * 60 * 24 * 7; // na 7 dní
    private final SecretKey Klic;

    public JWTUtils(){
        String tajnyKlic = "478573881d44a5a7ffb0564ee93db63fbefb09cc09e280fe553f12d5d8a5c02d278fbce1a3198f62e9aec0fbd665f7a87d33c3f346548828a8a9311b065dc1356ada9af762bb6b763d34644f86de7efc109033f94f6d41ca8707591544fdb2700ff57302882b682a9f352b83d794d5e5d1030cf833d369e4d8e36c02cf14525d0e263fa7f0f6b3e677782d147cfd485d13a2dac893205be9bb3fcd5e15ab074d8247b85f7858e193c7a2aeb37e47477dbc9ba46a45c8da9d884455f66e54b3bdc96e689e247839d617eb6a92c82079100dd9c8715e8185b24bc87301817f2303617cda6d544e9558ea05a38900e17bd67197c38bce6e4b53efa4a29d47677f8c";
        byte[] klicByty = Base64.getDecoder().decode(tajnyKlic.getBytes(StandardCharsets.UTF_8));
        this.Klic = new SecretKeySpec(klicByty, "HmacSHA256");
    }

    public String generatorTokenu(UserDetails detailyUzivatele){
        return Jwts.builder()
                .subject(detailyUzivatele.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Cas_Expirace))
                .signWith(Klic)
                .compact();
    }

    public String extrahujJmenoUzivatele(String token){
        return extrahujClaimy(token, Claims::getSubject);
    }
    //T = generic type
    private <T> T extrahujClaimy (String token, Function<Claims, T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(Klic).build().parseSignedClaims(token).getPayload());

    }
    public boolean jeTokenValidni(String token, UserDetails detailyUzivatele){
        final String jmenoUzivatele = extrahujJmenoUzivatele(token);
        return (jmenoUzivatele.equals(detailyUzivatele.getUsername()) && !jeTokenProsly(token));
    }
    private boolean jeTokenProsly(String token){
        return extrahujClaimy(token, Claims::getExpiration).before(new Date());
    }
}
