package br.com.shopflow.auth_service.controller;

import br.com.shopflow.auth_service.dto.AuthRequest;
import br.com.shopflow.auth_service.dto.AuthRespose;
import br.com.shopflow.auth_service.dto.RegisterRequest;
import br.com.shopflow.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthRespose> login(@RequestBody @Valid AuthRequest dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthRespose> register(@RequestBody @Valid RegisterRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }




}
