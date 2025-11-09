package br.ufpi.recicle_ai.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email;
    private String senha;
}