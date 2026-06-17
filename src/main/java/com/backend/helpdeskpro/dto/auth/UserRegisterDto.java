package com.backend.helpdeskpro.dto.auth;

public class UserRegisterDto {
    public String firstName;
    public String lastName;
    private String fullName;
    private String email;
    private String password;
    private String phone;

    public UserRegisterDto() {
    }

    public UserRegisterDto(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static UserResponseDto fromEntity(com.backend.helpdeskpro.entity.User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setAvatar_url(user.getAvatarUrl());
        dto.setFcm_token(user.getFcmToken());
        if (user.getDepartment() != null) {
            dto.setDepartmentId(user.getDepartment().getDepartmentId());
        }
        dto.setActive(user.getActive());
        return dto;
    }

}
