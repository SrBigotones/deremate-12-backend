package ar.edu.uade.deremateapp.back.model;


import ar.edu.uade.deremateapp.back.dto.UsuarioDTO;
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

    @Column(nullable = false) private String password;

    @Column(nullable = false) private String email;

    @Column(nullable = false) private String nombre;

    @Column(nullable = false) private String apellido;

    @Column(nullable = false) private int documento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false) private EstadoUsuario estado;

    public UsuarioDTO toUsuarioDTO() {
        return UsuarioDTO.builder()
                .username(username)
                .email(email)
                .nombre(nombre)
                .apellido(apellido)
                .documento(documento).build();
    }

    public boolean estaActivo() {
        return estado.equals(EstadoUsuario.ACTIVO);
    }
}
