package com.solaris.voxora.auth.service.impl;

import com.solaris.voxora.auth.dto.request.LoginRequestDTO;
import com.solaris.voxora.auth.dto.request.RegisterRequestDTO;
import com.solaris.voxora.auth.dto.response.AuthResponseDTO;
import com.solaris.voxora.auth.service.AuthService;
import com.solaris.voxora.auth.util.JwtUtil;
import com.solaris.voxora.common.exception.AppException;
import com.solaris.voxora.user.entity.Role;
import com.solaris.voxora.user.entity.User;
import com.solaris.voxora.user.entity.UserStatus;
import com.solaris.voxora.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AppException("Email already exists", HttpStatus.CONFLICT);
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .fullName(request.fullName())
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponseDTO(token, "Bearer");
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AppException(
                        "Invalid email or password",
                        HttpStatus.UNAUTHORIZED
                ));

        boolean isPasswordMatched = passwordEncoder.matches(
                request.password(),
                user.getPassword()
        );

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException(
                    "User account is inactive",
                    HttpStatus.UNAUTHORIZED
            );
        }

        if (!isPasswordMatched) {
            throw new AppException(
                    "Invalid email or password",
                    HttpStatus.UNAUTHORIZED
            );
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponseDTO(token, "Bearer");
    }
}