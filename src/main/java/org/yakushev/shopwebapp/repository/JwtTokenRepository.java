package org.yakushev.shopwebapp.repository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.yakushev.shopwebapp.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenRepository {
    private static final String BEARER_PREFIX = "Bearer ";

    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(HttpServletRequest httpServletRequest) {
        String id = UUID.randomUUID().toString().replace("-", "");
        Date now = new Date();
        Date exp = Date.from(LocalDateTime.now().plusMinutes(30)
                .atZone(ZoneId.systemDefault()).toInstant());

        String username = (String) httpServletRequest.getAttribute(User.class.getName());

        String token = Jwts.builder()
                    .setId(id)
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setNotBefore(now)
                    .setExpiration(exp)
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();

        User user = userRepository.findByUsername(username);
        user.setToken(token);
        userRepository.save(user);

        return token;
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
            String token = tokenFromHeader.substring(BEARER_PREFIX.length());
            String username = getUsernameFromToken(token);
            User user = userRepository.findByUsername(username);

            if (user != null && user.getToken().equals(token)) {
                return token;
            }
        }

        return null;
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

    public void auth(HttpServletRequest request) {
        String token = loadToken(request);

        if (StringUtils.isEmpty(token)) {
            throw new JwtAuthException("Invalid or empty JWT token.");
        }

        Date expirationDate = getExpirationDateFromToken(token);
        long diff = expirationDate.getTime() - (new Date()).getTime();

        if (diff <= 0) {
            throw new JwtAuthException("JWT token is expired.");
        }
    }
}
