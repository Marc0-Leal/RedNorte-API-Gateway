package com.example.RedNorte_API_GATEWAY.controller;

import com.example.RedNorte_API_GATEWAY.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(new Object() {
            public String mensaje = "✓ API Gateway funcionando";
            public long timestamp = System.currentTimeMillis();
        });
    }

    // ── GESTIÓN ──────────────────────────────────────────────────────
    @RequestMapping(value = "/hospital/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> hospital(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/cliente/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> cliente(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/medico/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> medico(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/citaMedica/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> citaMedica(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/listaEspera/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> listaEspera(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/pago/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> pago(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/comuna/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> comuna(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    @RequestMapping(value = "/region/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> region(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-gestion-osku.onrender.com");
    }

    // ── OPTIMIZACIÓN ─────────────────────────────────────────────────
    @RequestMapping(value = "/optimizacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> optimizacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-optimizacion.onrender.com");
    }

    // ── NOTIFICACIONES ───────────────────────────────────────────────
    @RequestMapping(value = "/comunicacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> comunicacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "https://rednorte-notificaciones.onrender.com");
    }
}