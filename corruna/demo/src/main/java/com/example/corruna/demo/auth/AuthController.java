/*package com.example.corruna.demo.auth;

import com.example.corruna.demo.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserDetailsService uds;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager am,
                          UserDetailsService uds,
                          JwtUtils jwtUtils) {
        this.authManager = am;
        this.uds = uds;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.contra())
        );

        UserDetails user = uds.loadUserByUsername(req.email());

        String token = jwtUtils.generateToken(req.email()); //se genera el token

        return ResponseEntity.ok(new AuthResponse(token));
    }
} */
