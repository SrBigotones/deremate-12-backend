package ar.edu.uade.deremateapp.back.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String direccionEntrega;

    private LocalDate fechaEntrega;

    private String estado;

    private String observaciones;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
