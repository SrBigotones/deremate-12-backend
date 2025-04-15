package ar.edu.uade.deremateapp.back.dto;

import ar.edu.uade.deremateapp.back.model.Usuario;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data @Builder
public class UsuarioDTO {
    private long id;
    private String username;
    private String password;
    private String email;
    private String nombre;
    private String apellido;
    private int documento;

    public Usuario toUsuario() {

        return Usuario.builder()
                .username(this.username)
                .password(this.password)
                .email(this.email)
                .nombre(this.nombre)
                .apellido(this.apellido)
                .documento(this.documento).build();
    }
}
