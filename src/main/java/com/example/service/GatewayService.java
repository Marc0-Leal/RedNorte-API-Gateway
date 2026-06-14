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

@Service
public class GatewayService {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<?> forwardRequest(HttpServletRequest request, String targetUrl) {
        try {
            String path = request.getRequestURI(); // ← sin el .replace()
            String fullUrl = targetUrl + path;

            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (!headerName.equalsIgnoreCase("host")) {
                    headers.add(headerName, request.getHeader(headerName));
                }
            }

            HttpEntity<?> entity = new HttpEntity<>(headers);
            HttpMethod method = HttpMethod.valueOf(request.getMethod());
            ResponseEntity<?> response = restTemplate.exchange(fullUrl, method, entity, String.class);
            return response;

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error enrutando request: " + e.getMessage());
        }
    }
}