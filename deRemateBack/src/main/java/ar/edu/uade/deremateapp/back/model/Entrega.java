package ar.edu.uade.deremateapp.back.model;

import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionEntrega;
    private String direccionDeposito;

    private LocalDateTime fechaEntrega;
    private LocalDateTime fechaCreacion;

    private EstadoEntrega estado;

    private String observaciones;

    @Column(nullable = false)
    private String emailCliente;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(nullable = true)
    private Integer calificacion;
    private String comentario;

    @Column(name = "imagen")
    private String imagen;


    public EntregaDTO convertirADTO() {
        EntregaDTO dto = new EntregaDTO();
        dto.setId(this.getId());
        dto.setDireccion(this.getDireccionEntrega());
        dto.setDireccionDeposito(this.getDireccionDeposito());
        dto.setEstado(this.getEstado());
        dto.setFechaCreacion(this.getFechaCreacion());
        dto.setFechaEntrega(this.getFechaEntrega());
        dto.setObservaciones(this.getObservaciones());
        dto.setUsuarioId(this.getUsuario().getId());
        dto.setEmailCliente(this.getEmailCliente());

        dto.setCalificacion(this.getCalificacion());
        dto.setComentario(this.getComentario());
        dto.setImagen(this.getImagen());

        return dto;
    }
}

