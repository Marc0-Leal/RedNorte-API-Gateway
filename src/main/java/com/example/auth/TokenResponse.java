package com.example.auth;


public record TokenResponse(
    String tokenType,
    String accessToken,
    long expiresInSeconds
) {
}
