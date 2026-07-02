package com.backend.helpdeskpro.dto.auth;

import com.backend.helpdeskpro.enums.UserRole;

public class UserUpdateDto {
    private String fullName;
    private String email;
    private UserRole role;
    private String phone;
    //getters and setters
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
    public UserRole getRole() {
        return role;
    }
    public void setRole(UserRole role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
