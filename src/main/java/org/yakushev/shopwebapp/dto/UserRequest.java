package org.yakushev.shopwebapp.dto;

import lombok.Data;
import org.yakushev.shopwebapp.model.User;

@Data
public class UserRequest {
    private Long id;
    private String username;
    private String password;
    private String role;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        if (username.equals("admin")) {
            user.setRole("admin");
        } else {
            user.setRole(role);
        }

        return user;
    }
}
