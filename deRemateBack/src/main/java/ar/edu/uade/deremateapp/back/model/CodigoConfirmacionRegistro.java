package ar.edu.uade.deremateapp.back.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CodigoConfirmacionRegistro {

    @Id
    private Long usuarioId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @Column(nullable = false, length = 6)
    private String codigo;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    public CodigoConfirmacionRegistro(Usuario usuario, String codigo) {
        this.usuario = usuario;
        this.codigo = codigo;
        this.fechaGeneracion = LocalDateTime.now();
    }
}
