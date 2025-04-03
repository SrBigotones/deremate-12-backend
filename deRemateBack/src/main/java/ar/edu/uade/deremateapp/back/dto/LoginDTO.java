package ar.edu.uade.deremateapp.back.dto;

import ar.edu.uade.deremateapp.back.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

    public Usuario toPersona(){
        Usuario p = new Usuario();
        p.setEmail(email);
        p.setPassword(password);

        return p;
    }
}