package org.yakushev.shopwebapp.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
