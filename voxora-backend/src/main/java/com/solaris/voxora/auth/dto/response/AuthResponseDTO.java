package com.solaris.voxora.auth.dto.response;

public record AuthResponseDTO(
        String accessToken,
        String tokenType
) {
}