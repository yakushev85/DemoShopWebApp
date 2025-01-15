package org.yakushev.shopwebapp.dto;

import lombok.Data;
import org.yakushev.shopwebapp.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String role;
    private Date createdAt;

    public static UserResponse fromUser(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setRole(user.getRole());
        userResponse.setCreatedAt(user.getCreatedAt());

        return userResponse;
    }

    public static List<UserResponse> fromUsers(List<User> all) {
        ArrayList<UserResponse> userResponseArrayList = new ArrayList<>();

        for (User user : all) {
            userResponseArrayList.add(fromUser(user));
        }

        return userResponseArrayList;
    }
}
