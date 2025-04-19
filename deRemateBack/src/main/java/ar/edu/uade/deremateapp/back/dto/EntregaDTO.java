package ar.edu.uade.deremateapp.back.dto;

import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class EntregaDTO {
    private Long id;
    private String direccion;
    private EstadoEntrega estado;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCreacion;
    private String observaciones;
    private Long usuarioId;
}
