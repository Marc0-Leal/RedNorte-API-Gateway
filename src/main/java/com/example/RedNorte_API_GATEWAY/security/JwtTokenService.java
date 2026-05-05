package com.example.RedNorte_API_GATEWAY.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

    private final String secret;
    private final String expectedIssuer;

    public JwtTokenService(
        @Value("${app.security.jwt.secret}") String secret,
        @Value("${app.security.jwt.issuer:}") String expectedIssuer
    ) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) {
            throw new IllegalArgumentException("JWT secret must be at least 32 bytes");
        }
        this.secret = secret;
        this.expectedIssuer = expectedIssuer;
    }

    public JWTClaimsSet validate(String token) {
        try {
            SignedJWT signedJwt = SignedJWT.parse(token);
            MACVerifier verifier = new MACVerifier(secret.getBytes(StandardCharsets.UTF_8));

            if (!JWSAlgorithm.HS256.equals(signedJwt.getHeader().getAlgorithm())) {
                throw new IllegalArgumentException("Unsupported JWT algorithm");
            }

            if (!signedJwt.verify(verifier)) {
                throw new IllegalArgumentException("Invalid JWT signature");
            }

            JWTClaimsSet claims = signedJwt.getJWTClaimsSet();
            validateClaims(claims);
            return claims;
        } catch (ParseException | JOSEException ex) {
            throw new IllegalArgumentException("Invalid JWT token", ex);
        }
    }

    public String generate(String subject, Collection<String> roles, long expiresInSeconds) {
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("Subject is required");
        }

        long ttl = expiresInSeconds > 0 ? expiresInSeconds : 3600;
        Instant now = Instant.now();
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
            .subject(subject)
            .issuer(expectedIssuer)
            .issueTime(Date.from(now))
            .expirationTime(Date.from(now.plusSeconds(ttl)))
            .claim("roles", roles == null ? java.util.List.of() : roles)
            .build();

        try {
            SignedJWT signedJwt = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.HS256).build(),
                claims
            );
            signedJwt.sign(new MACSigner(secret.getBytes(StandardCharsets.UTF_8)));
            return signedJwt.serialize();
        } catch (JOSEException ex) {
            throw new IllegalStateException("Could not sign JWT token", ex);
        }
    }

    private void validateClaims(JWTClaimsSet claims) {
        String subject = claims.getSubject();
        if (subject == null || subject.isBlank()) {
            throw new IllegalArgumentException("JWT subject is required");
        }

        Date expiration = claims.getExpirationTime();
        if (expiration == null || expiration.toInstant().isBefore(Instant.now())) {
            throw new IllegalArgumentException("JWT is expired");
        }

        if (expectedIssuer != null && !expectedIssuer.isBlank()) {
            String issuer = claims.getIssuer();
            if (!expectedIssuer.equals(issuer)) {
                throw new IllegalArgumentException("JWT issuer is invalid");
            }
        }
    }
}