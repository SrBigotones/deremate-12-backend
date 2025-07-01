package ar.edu.uade.deremateapp.back.dto;

import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class EntregaConQRDTO {
    private Long id;
    private String direccionEntrega;
    private String direccionDeposito;
    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCreacion;
    private EstadoEntrega estado;
    private String observaciones;
    private String emailCliente;
    private Long usuarioId;
    private Integer calificacion;
    private String comentario;
    private String qrBase64;

    public static EntregaConQRDTO fromEntrega(Entrega entrega) {
        EntregaConQRDTO dto = new EntregaConQRDTO();
        dto.setId(entrega.getId());
        dto.setDireccionEntrega(entrega.getDireccionEntrega());
        dto.setDireccionDeposito(entrega.getDireccionDeposito());
        dto.setEstado(entrega.getEstado());
        dto.setFechaCreacion(entrega.getFechaCreacion());
        dto.setFechaEntrega(entrega.getFechaEntrega());
        dto.setObservaciones(entrega.getObservaciones());
        dto.setEmailCliente(entrega.getEmailCliente());
        dto.setUsuarioId(entrega.getUsuario() != null ? entrega.getUsuario().getId() : null);
        dto.setCalificacion(entrega.getCalificacion());
        dto.setComentario(entrega.getComentario());
        return dto;
    }
} 