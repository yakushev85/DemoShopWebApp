package org.yakushev.shopwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.yakushev.shopwebapp.dto.PasswordRequest;
import org.yakushev.shopwebapp.dto.UserRequest;
import org.yakushev.shopwebapp.dto.UserResponse;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.security.JwtTokenRepository;
import org.yakushev.shopwebapp.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<User> getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                             @RequestParam(name="size", defaultValue = "10") Integer size,
                             HttpServletRequest request) {
        jwtTokenRepository.auth(request);
        userService.checkAdminRole(request);
        return userService.getAll(PageRequest.of(page, size));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public UserResponse getItemById(@PathVariable Long id, HttpServletRequest request) {
        jwtTokenRepository.auth(request);
        userService.checkAdminRole(request);
        return UserResponse.fromUser(userService.getById(id));
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @Transactional
    public UserResponse add(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        jwtTokenRepository.auth(request);
        userService.checkAdminRole(request);
        return UserResponse.fromUser(userService.add(userRequest.toUser()));
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Transactional
    public UserResponse update(@RequestBody UserRequest userRequest, HttpServletRequest request) {
        jwtTokenRepository.auth(request);
        userService.checkAdminRole(request);
        return UserResponse.fromUser(userService.update(userRequest.toUser()));
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @Transactional
    public UserResponse setPassword(@RequestBody PasswordRequest passwordRequest, HttpServletRequest request) {
        jwtTokenRepository.auth(request);
        User user = userService.getUserFromRequest(request);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        if (!passwordEncoder.matches(passwordRequest.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong password.");
        }

        return UserResponse.fromUser(userService.update(user));
    }
}
