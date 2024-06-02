package com.java.year3.loan_api.controller;

import com.java.year3.loan_api.jwt.dto.request.AuthRequestDTO;
import com.java.year3.loan_api.jwt.dto.request.UserRegisterRequestDTO;
import com.java.year3.loan_api.jwt.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequestDTO request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequestDTO request) throws AccessDeniedException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
