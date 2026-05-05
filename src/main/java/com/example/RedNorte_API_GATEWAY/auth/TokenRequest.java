package com.example.RedNorte_API_GATEWAY.auth;

import java.util.List;

public record TokenRequest (
    String subject,
    List<String> roles,
    Long expiresInSeconds
)    {
}
