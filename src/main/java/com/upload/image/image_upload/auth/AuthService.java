package com.upload.image.image_upload.auth;

import com.upload.image.image_upload.config.JwtServices;
import com.upload.image.image_upload.entity.Role;
import com.upload.image.image_upload.entity.User;
import com.upload.image.image_upload.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServices jwtServices;

    public AuthenticationResponse authenticate(RegisterRequest authentication) {
       User user= userRepository.save(
                User.builder()
                        .firstname(authentication.getFirstname())
                        .password(passwordEncoder.encode(authentication.getPassword()))
                        .email(authentication.getEmail())
                        .phone(authentication.getPhone())
                        .role(Role.valueOf(authentication.getRole()))
                        .build()
        );
        String token =jwtServices.generateToken(user);
        return AuthenticationResponse.builder().token(token).build();
    }
}

