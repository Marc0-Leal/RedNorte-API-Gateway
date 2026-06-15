package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RedNorteApiGatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RedNorteApiGatewayApplication.class, args);
        
        // Debug: ver si SecurityConfig está registrado
        System.out.println("=== BEANS DE SEGURIDAD ===");
        for (String name : ctx.getBeanDefinitionNames()) {
            if (name.toLowerCase().contains("security") || 
                name.toLowerCase().contains("jwt") ||
                name.toLowerCase().contains("filter")) {
                System.out.println("BEAN: " + name);
            }
        }
        System.out.println("=== FIN BEANS ===");
    }
}