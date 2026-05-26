package com.solaris.voxora.auth.service;


import com.solaris.voxora.auth.dto.request.LoginRequestDTO;
import com.solaris.voxora.auth.dto.request.RegisterRequestDTO;
import com.solaris.voxora.auth.dto.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(RegisterRequestDTO request);

    AuthResponseDTO login(LoginRequestDTO request);
}