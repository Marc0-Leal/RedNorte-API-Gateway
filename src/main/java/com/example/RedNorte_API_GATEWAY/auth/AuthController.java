package com.example.RedNorte_API_GATEWAY.auth;

import com.example.RedNorte_API_GATEWAY.security.JwtTokenService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;

    public AuthController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody TokenRequest request) {
        if (request == null || request.subject() == null || request.subject().isBlank()) {
            return ResponseEntity.badRequest().body("subject is required");
        }

        long expiresInSeconds = request.expiresInSeconds() == null ? 3600 : request.expiresInSeconds();
        String token = jwtTokenService.generate(
            request.subject(),
            request.roles() == null ? List.of() : request.roles(),
            expiresInSeconds
        );

        TokenResponse response = new TokenResponse("Bearer", token, expiresInSeconds);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}