package ar.edu.uade.deremateapp.back.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private long id;

    @Column(nullable = false) private String username;

    @Column(nullable = false) private String email;

    @Column(nullable = false) private String password;

    @Column(nullable = false) private String nombre;

    @Column(nullable = false) private String apellido;

    @Column(nullable = false) private int documento;
}
