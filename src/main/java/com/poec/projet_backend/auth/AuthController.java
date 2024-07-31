package com.poec.projet_backend.auth;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register/mentor")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) throws Exception {
        System.out.println(request.toString());
        return ResponseEntity.ok(service.registerMentor(request, httpRequest));
    }

    @PostMapping("/register/admin")
    public ResponseEntity<Map<String, String>> registerAdmin(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) throws Exception {
        System.out.println(request.toString());
        return ResponseEntity.ok(service.registerAdmin(request, httpRequest));
    }

    @PostMapping("/register/student")
    public ResponseEntity<Map<String, String>> registerStudent(@RequestBody RegisterRequest request, HttpServletRequest httpRequest) throws Exception {
        System.out.println(request.toString());
        return ResponseEntity.ok(service.registerStudent(request, httpRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest request, HttpServletRequest httpRequest) {
    AuthResponse authenticationResponse = service.authenticate(request, httpRequest);
    return ResponseEntity.ok(authenticationResponse);

    }
}
