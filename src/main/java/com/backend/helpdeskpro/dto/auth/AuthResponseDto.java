package com.backend.helpdeskpro.dto.auth;

import com.backend.helpdeskpro.entity.User;

public class AuthResponseDto {
    private String fullName;
    private String email;
    private String token;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static AuthResponseDto authResponseDto(
            User user,
            String token) {
        AuthResponseDto dto = new AuthResponseDto();
        dto.setEmail(user.getEmail());
        dto.setToken(token);
        dto.setFullName(user.getFullName());
        return dto;
    }
}