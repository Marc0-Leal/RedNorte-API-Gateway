package com.example.RedNorte_API_GATEWAY.auth;


public record TokenResponse(
    String tokenType,
    String accessToken,
    long expiresInSeconds
) {
}
