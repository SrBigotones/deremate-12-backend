package ar.edu.uade.deremateapp.back.controllers;


import ar.edu.uade.deremateapp.back.dto.LoginDTO;
import ar.edu.uade.deremateapp.back.dto.UsuarioDTO;
import ar.edu.uade.deremateapp.back.exceptions.CamposVaciosException;
import ar.edu.uade.deremateapp.back.exceptions.MailAlreadyUsedException;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.security.JwtTokenUtil;
import ar.edu.uade.deremateapp.back.services.AuthService;
import ar.edu.uade.deremateapp.back.services.UserService;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private AuthService authService;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private UserService userService;


    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO loginDto){

        Usuario p = loginDto.toPersona();
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationRequest);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        Usuario usu = SecurityUtils.getCurrentUser();

        String jwtToken = jwtTokenUtil.generateToken(loginDto.getEmail());

        return ResponseEntity.ok(Map.of("jwtToken", jwtToken));

    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioDTO> guardarPersona(@Valid @RequestBody UsuarioDTO user) throws CamposVaciosException, MailAlreadyUsedException {
        // Verifica si algún campo está vacío o contiene solo espacios en blanco
        if (user.getNombre().trim().isEmpty() || user.getApellido().trim().isEmpty() || user.getEmail().trim().isEmpty() ||
                user.getPassword().trim().isEmpty() || user.getUsername().trim().isEmpty()) {
            throw new CamposVaciosException("No se pueden dejar espacios vacíos");
        }

        if (userService.buscarUsuarioPorMail(user.getEmail()).isPresent()) {
            throw new MailAlreadyUsedException();
        }

        Usuario usuario = user.toUsuario();
        Usuario nuevaPersona = authService.registrarPersona(usuario);

        UsuarioDTO usuarioDTO = nuevaPersona.toUsuarioDTO();
        return ResponseEntity.ok(usuarioDTO);
    }

}
