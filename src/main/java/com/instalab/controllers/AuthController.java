package com.instalab.controllers;

import com.instalab.dtos.requests.AuthRequest;
import com.instalab.dtos.responses.AuthResponse;
import com.instalab.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthenticationManager authManager;
  private final JwtTokenProvider tokenProvider;

  public AuthController(AuthenticationManager authManager,
                        JwtTokenProvider tokenProvider) {
    this.authManager = authManager;
    this.tokenProvider = tokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
    Authentication auth = authManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.username(), req.password())
    );
    String token = tokenProvider.generateToken(auth);
    return ResponseEntity.ok(new AuthResponse(token));
  }
}
