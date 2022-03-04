package com.bartczak.zai.lodging.auth.jwt;

import com.bartczak.zai.lodging.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@Slf4j
public class JwtTokenUtil {

    @Value("${security.jwt.secret:secret}")
    private String jwtSecret;
    @Value("${security.jwt.issuer:lodging.com}")
    private String jwtIssuer;
    @Value("${security.jwt.expiration:3600}")
    private Long jwtExpiration;

    public String generateJwt(User user) {
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.valueOf(claims.getSubject());
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.warn("Invalid JWT token {} - {}", token, e.getMessage());
        }
        return false;
    }

}
