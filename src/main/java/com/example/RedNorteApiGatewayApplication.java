package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(excludeName = {
    "org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
})
public class RedNorteApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RedNorteApiGatewayApplication.class, args);
    }
}