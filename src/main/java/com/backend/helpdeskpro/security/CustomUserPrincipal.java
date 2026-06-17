package com.backend.helpdeskpro.security;

import java.util.Collection;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.helpdeskpro.entity.User;

import java.util.Collections;

public class CustomUserPrincipal implements UserDetails {

    private User user;

    public CustomUserPrincipal(User user) {
        this.user = user;

    }

    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole().toString();

        if (role.equals("END_USER")) {
            role = "ROLE_END_USER";
        } else if (role.equals("AGENT")) {
            role = "ROLE_AGENT";
        } else if (role.equals("TEAM_LEAD")) {
            role = "ROLE_TEAM_LEAD";

        } else {
            role = "ROLE_ADMIN";
        }
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

}