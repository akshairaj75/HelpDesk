package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuthProvider;
import com.backend.helpdeskpro.enums.UserRole;
import com.backend.helpdeskpro.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oauthUser = super.loadUser(request);

        try {

            String email = oauthUser.getAttribute("email");
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");
            String providerId = oauthUser.getAttribute("sub");

            System.out.println("EMAIL: " + email);

            User user = userRepository.findByEmailIgnoreCase(email).orElse(new User());

            user.setEmail(email);
            user.setFullName(name);
            user.setAvatarUrl(picture);
            user.setAuthProvider(AuthProvider.GOOGLE);
            user.setProviderId(providerId);
            user.setActive(true);
            user.setActive(true);
            user.setRole(UserRole.STAFF);
            user.setLastLoginAt(LocalDateTime.now());
            User saved = userRepository.save(user);

            System.out.println("USER SAVED: " + saved.getEmail());

        } catch (Exception e) {

            System.out.println("OAUTH SAVE FAILED");

            e.printStackTrace();
        }

        return oauthUser;
    }

}
