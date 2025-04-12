package org.yakushev.shopwebapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.AuthRequest;
import org.yakushev.shopwebapp.dto.AuthResponse;
import org.yakushev.shopwebapp.dto.UserRequest;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.repository.JwtTokenRepository;
import org.yakushev.shopwebapp.service.UserService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Transactional
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AuthResponse loginUser(@RequestBody AuthRequest authRequest,
                                  HttpServletRequest request, HttpServletResponse response) {
        User resolvedUser = userService.findByUsername(authRequest.getUsername());

        if (resolvedUser != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if (!passwordEncoder.matches(authRequest.getPassword(), resolvedUser.getPassword())) {
                throw new IllegalArgumentException("Wrong password.");
            }

            if (resolvedUser.getRole() != null && resolvedUser.getRole().equalsIgnoreCase("locked")) {
                throw new IllegalArgumentException("User is locked.");
            }

            request.setAttribute(User.class.getName(), resolvedUser.getUsername());
            String token = jwtTokenRepository.generateToken(request);
            jwtTokenRepository.saveToken(token, request, response);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(resolvedUser.getId());
            authResponse.setUsername(resolvedUser.getUsername());
            authResponse.setToken(token);
            authResponse.setRole(resolvedUser.getRole());

            return authResponse;
        } else {
            throw new IllegalArgumentException("Wrong username.");
        }
    }

    @Transactional
    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public AuthResponse signupUser(@RequestBody UserRequest userRequest, HttpServletRequest request, HttpServletResponse response) {
        User resolvedUser = userService.findByUsername(userRequest.getUsername());

        if (resolvedUser == null) {
            User storedUser = userService.add(userRequest.toUser());

            if (storedUser != null) {
                request.setAttribute(User.class.getName(), storedUser.getUsername());
                String token = jwtTokenRepository.generateToken(request);
                jwtTokenRepository.saveToken(token, request, response);

                AuthResponse authResponse = new AuthResponse();
                authResponse.setId(storedUser.getId());
                authResponse.setUsername(storedUser.getUsername());
                authResponse.setToken(token);
                authResponse.setRole(storedUser.getRole());

                return authResponse;
            }
        }

        throw new IllegalArgumentException("Username is already used.");
    }
}
