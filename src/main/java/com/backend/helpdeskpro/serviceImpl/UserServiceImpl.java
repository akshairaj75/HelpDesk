package com.backend.helpdeskpro.serviceImpl;

import com.backend.helpdeskpro.dto.auth.AuthResponseDto;
import com.backend.helpdeskpro.dto.auth.UserLoginDto;
import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuthProvider;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.service.UserService;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Transactional
    @Override
    public AuthResponseDto registerUser(UserRegisterDto dto) {

        String fullName;
        if (dto.getLastName() != null && !dto.getLastName().trim().isEmpty()) {
            fullName = dto.getFirstName() + " " + dto.getLastName();
        } else {
            fullName = dto.getFirstName();
        }
        User user = new User();

        user.setFullName(fullName);
        user.setEmail(dto.getEmail());
        user.setPasswordHash(
                passwordEncoder.encode(
                        dto.getPassword()));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(user);

        return AuthResponseDto.authResponseDto(savedUser, token);
    }

    @Override
    public AuthResponseDto login(UserLoginDto dto) {
        User user = userRepository.findByEmailIgnoreCase(
                dto.getEmail()).orElseThrow();

        if (user.getAuthProvider() != AuthProvider.LOCAL) {
            throw new RuntimeException(
                    "Use Google login");
        }

        boolean matches = passwordEncoder.matches(
                dto.getPassword(),
                user.getPasswordHash());

        if (!matches) {
            throw new RuntimeException(
                    "Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDto.authResponseDto(user, token);

    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> res = userRepository.findAll();
        return res.stream()
                .map(UserRegisterDto::fromEntity)
                .toList();
    }

}
