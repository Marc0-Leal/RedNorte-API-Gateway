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

    @RequestMapping(value = "/hospital/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> hospital(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8081");
    }

    @RequestMapping(value = "/optimizacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> optimizacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8082");
    }

    @RequestMapping(value = "/comunicacion/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> comunicacion(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8083");
    }

    @RequestMapping(value = "/reportes/**", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
    public ResponseEntity<?> reportes(HttpServletRequest request) {
        return gatewayService.forwardRequest(request, "http://localhost:8084");
    }
}
