package org.yakushev.shopwebapp.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.yakushev.shopwebapp.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenRepository {
    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String username = (String) httpServletRequest.getAttribute(User.class.getName());

        return Jwts.builder()
                    .setId(id)
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
    }

    public void saveToken(String token, HttpServletRequest request, HttpServletResponse response) {
        if (token != null) {
            if (response.getHeaderNames().contains(HttpHeaders.AUTHORIZATION)) {
                response.setHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
            } else {
                response.addHeader(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + token);
            }
        }
    }

    public String loadToken(HttpServletRequest request) {
        String tokenFromHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (tokenFromHeader != null) {
            return tokenFromHeader.substring(BEARER_PREFIX.length());
        } else {
            return null;
        }
    }

    public void clearToken(HttpServletResponse response) {
        if (response.getHeaderNames().contains(HttpHeaders.AUTHORIZATION))
            response.setHeader(HttpHeaders.AUTHORIZATION, "");
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }
}
