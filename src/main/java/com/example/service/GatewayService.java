package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.io.IOException;

@Service
public class GatewayService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> forwardRequest(HttpServletRequest request, String targetUrl) {
        try {
            String path = request.getRequestURI();
            String fullUrl = targetUrl + path;
            HttpHeaders requestHeaders = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (!headerName.equalsIgnoreCase("host") &&
                    !headerName.equalsIgnoreCase("accept-encoding")) {
                    requestHeaders.add(headerName, request.getHeader(headerName));
                }
            }
            String body = null;
            try {
                body = new String(request.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            } catch (IOException e) {
                body = "";
            }

            HttpEntity<String> entity = new HttpEntity<>(body.isEmpty() ? null : body, requestHeaders);
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            ResponseEntity<String> response = restTemplate.exchange(fullUrl, method, entity, String.class);

            HttpHeaders responseHeaders = new HttpHeaders();
            response.getHeaders().forEach((key, values) -> {
                if (!key.equalsIgnoreCase("Access-Control-Allow-Origin") &&
                    !key.equalsIgnoreCase("Access-Control-Allow-Methods") &&
                    !key.equalsIgnoreCase("Access-Control-Allow-Headers") &&
                    !key.equalsIgnoreCase("Transfer-Encoding")) {
                    responseHeaders.addAll(key, values);
                }
            });

            return ResponseEntity.status(response.getStatusCode())
                .headers(responseHeaders)
                .body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error enrutando request: " + e.getMessage());
        }
    }
}