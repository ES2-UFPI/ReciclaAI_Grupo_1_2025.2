package br.ufpi.recicle_ai.controller;

import br.ufpi.recicle_ai.dto.LoginRequestDTO;
import br.ufpi.recicle_ai.dto.LoginResponseDTO;
import br.ufpi.recicle_ai.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }
}