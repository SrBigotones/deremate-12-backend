package ar.edu.uade.deremateapp.back.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class CodigoConfirmacionEntrega {

    @Id
    private Long entregaId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Entrega entrega;

    @Column(nullable = false, length = 6)
    private String codigo;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    public CodigoConfirmacionEntrega(Entrega entrega, String codigo) {
        this.entrega = entrega;
        this.codigo = codigo;
        this.fechaGeneracion = LocalDateTime.now();
    }
}
