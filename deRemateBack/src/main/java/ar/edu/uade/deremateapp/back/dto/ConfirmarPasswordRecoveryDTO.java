package ar.edu.uade.deremateapp.back.dto;

import lombok.Data;

@Data
public class ConfirmarPasswordRecoveryDTO {
    private String email;
    private String codigo;
    private String password;
}
