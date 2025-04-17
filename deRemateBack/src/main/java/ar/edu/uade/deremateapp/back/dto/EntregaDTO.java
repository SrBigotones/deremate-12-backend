package ar.edu.uade.deremateapp.back.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class EntregaDTO {
    private Long id;
    private String direccion;
    private String estado;
    private LocalDate fecha;
    private String observaciones;
    private Long usuarioId;
}
