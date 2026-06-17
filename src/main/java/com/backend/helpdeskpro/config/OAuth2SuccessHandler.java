package com.backend.helpdeskpro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuthProvider;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.serviceImpl.JwtService;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

        @Override
        public void onAuthenticationSuccess(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {

                OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

                String email = oauthUser.getAttribute("email");
                String name = oauthUser.getAttribute("name");
                String picture = oauthUser.getAttribute("picture");
                String providerId = oauthUser.getAttribute("sub");

                User user = userRepository.findByEmailIgnoreCase(email)
                                .orElseGet(() -> {

                                        User newUser = new User();
                                        newUser.setEmail(email);
                                        newUser.setFullName(name);
                                        newUser.setAvatarUrl(picture);
                                        newUser.setProviderId(providerId);
                                        newUser.setAuthProvider(AuthProvider.GOOGLE);
                                        newUser.setActive(true);

                                        return userRepository.save(newUser);
                                });

                String token = jwtService.generateToken(user);

                response.sendRedirect(
                                "http://localhost:4200/oauth-success?token="
                                                + token);
        }
}
