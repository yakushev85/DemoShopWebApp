package org.yakushev.shopwebapp.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    private final JwtTokenRepository jwtTokenRepository;

    public JwtFilter(JwtTokenRepository jwtTokenRepository, HandlerExceptionResolver resolver) {
        this.jwtTokenRepository = jwtTokenRepository;
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (SecurityHelper.isPublicUrl(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenRepository.loadToken(request);

        if (StringUtils.isEmpty(token)) {
            resolver.resolveException(request, response, null, new JwtAuthException("JWT token is required."));
            return;
        }

        String username = jwtTokenRepository.getUsernameFromToken(token);

        if (StringUtils.isEmpty(username)) {
            resolver.resolveException(request, response, null, new JwtAuthException("Invalid JWT token."));
            return;
        }

        Date expirationDate = jwtTokenRepository.getExpirationDateFromToken(token);
        long diff = expirationDate.getTime() - (new Date()).getTime();

        if (diff > 0) {
            filterChain.doFilter(request, response);
        } else {
            resolver.resolveException(request, response, null, new JwtAuthException("JWT token is expired."));
        }
    }
}
