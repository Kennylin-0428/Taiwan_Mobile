package com.example.auth.controller;

import com.example.auth.dto.AuthResponse;
import com.example.auth.dto.LoginRequest;
import com.example.auth.dto.MagicLinkRequest;
import com.example.auth.dto.MagicLoginRequest;
import com.example.auth.dto.RegisterRequest;
import com.example.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService auth;

  public AuthController(AuthService auth) {
    this.auth = auth;
  }

  @PostMapping(value = "/register", consumes = "application/x-www-form-urlencoded")
  public ResponseEntity<AuthResponse> register(@Valid @ModelAttribute RegisterRequest req) {
    auth.register(req);
    return ResponseEntity.ok(new AuthResponse("User registered successfully."));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
    return ResponseEntity.ok(auth.login(req));
  }

  @PostMapping("/magiclink/request")
  public ResponseEntity<AuthResponse> requestMagic(@RequestBody @Valid MagicLinkRequest req) {
    AuthResponse AuthResponse = auth.issueMagicLink(req.getEmail());
    return ResponseEntity.ok(AuthResponse);
  }

  @PostMapping("/magic/login")
  public ResponseEntity<AuthResponse> consumeMagicPost(@RequestBody MagicLoginRequest request) {
    return ResponseEntity.ok(auth.consumeMagicToken(request.getToken()));
  }

}
