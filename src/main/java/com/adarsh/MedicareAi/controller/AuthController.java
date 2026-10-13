package com.adarsh.MedicareAi.controller;

import com.adarsh.MedicareAi.dto.LoginRequest;
import com.adarsh.MedicareAi.dto.LoginResponse;
import com.adarsh.MedicareAi.dto.RegisterRequest;
import com.adarsh.MedicareAi.dto.RegisterResponse;
import com.adarsh.MedicareAi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register") // @Valid use for apply validation written in RegisterReq DTO
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){

        RegisterResponse response = authService.register(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
