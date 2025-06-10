package ar.edu.uade.deremateapp.back.controllers;

import ar.edu.uade.deremateapp.back.dto.UsuarioDTO;
import ar.edu.uade.deremateapp.back.exceptions.UsuarioAutenticadoNoEncontradoException;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @GetMapping()
    public ResponseEntity<UsuarioDTO> obtenerDetalleUsuario() throws UsuarioAutenticadoNoEncontradoException {
        Usuario user = SecurityUtils.getCurrentUser();
        return ResponseEntity.ok(user.toUsuarioDTO());
    }
}
