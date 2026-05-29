package com.example.RedNorte_API_GATEWAY.controller;

import com.example.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    /**
     * ENDPOINT DE PRUEBA
     * GET http://localhost:8080/api/test
     */
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new Object() {
            public String mensaje = "✓ API Gateway funcionando";
            public long timestamp = System.currentTimeMillis();
        });
    }

    /**
     * RUTAS CLÍNICA
     * GET/POST http://localhost:8080/api/clinica/**
     */
    @RequestMapping(value = "/clinica/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> clinica(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8081");
    }

    /**
     * RUTAS OPTIMIZACIÓN
     */
    @RequestMapping(value = "/optimizacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> optimizacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8082");
    }

    /**
     * RUTAS COMUNICACIÓN
     */
    @RequestMapping(value = "/comunicacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> comunicacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8083");
    }

    /**
     * RUTAS REPORTES
     */
    @RequestMapping(value = "/reportes/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
    public ResponseEntity<?> reportes(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8084");
    }
}