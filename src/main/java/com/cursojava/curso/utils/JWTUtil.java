package com.cursojava.curso.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttlMillis}")
    private long ttlMillis;

    private final Logger log = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * Helper para obtener la llave secreta en el formato actual
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Create a new token.
     */
    public String create(String id, String subject) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // En JJWT 0.12.x, el builder usa métodos más descriptivos
        var builder = Jwts.builder()
                .id(id)
                .issuedAt(now)
                .subject(subject)
                .issuer(issuer)
                .signWith(getSigningKey()); // Detecta automáticamente HS256

        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.expiration(exp);
        }

        return builder.compact();
    }

    /**
     * Method to validate and read the JWT
     */
    public String getValue(String jwt) {
        // En la versión nueva se usa parser() -> verifyWith() -> build()
        try {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return claims.getSubject();
        } catch (ExpiredJwtException e) {
        log.error("Token expirado: {}", e.getMessage());
        return null;
        } catch (JwtException e) {
        log.error("Token inválido: {}", e.getMessage());
        return null;
    }
    }

    /**
     * Method to validate and read the JWT
     */
    public String getKey(String jwt) {
        try{
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();

        return claims.getId();
        } catch (ExpiredJwtException e) {
        log.error("Token expirado: {}", e.getMessage());
        return null;
        } catch (JwtException e) {
        log.error("Token inválido: {}", e.getMessage());
        return null;
        }
    }

    /**
     * Para mayor seguridad *
     * @return true si el token es válido, false si no
     */
    public boolean isTokenValid(String jwt) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(jwt);
            return true;
        } catch (JwtException e) {
            log.error("Token inválido: {}", e.getMessage());
            return false;
        }
    }

}