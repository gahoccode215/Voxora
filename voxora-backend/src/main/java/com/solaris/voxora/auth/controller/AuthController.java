package com.solaris.voxora.auth.controller;

import com.solaris.voxora.auth.dto.request.LoginRequestDTO;
import com.solaris.voxora.auth.dto.request.RegisterRequestDTO;
import com.solaris.voxora.auth.service.AuthService;
import com.solaris.voxora.common.dto.ApiResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDTO<?>> register(
            @Valid @RequestBody RegisterRequestDTO request
    ) {

        return ResponseEntity.ok(
                ApiResponseDTO.success(
                        "Register successfully",
                        authService.register(request)
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDTO<?>> login(
            @Valid @RequestBody LoginRequestDTO request
    ) {

        return ResponseEntity.ok(
                ApiResponseDTO.success(
                        "Login successfully",
                        authService.login(request)
                )
        );
    }
}