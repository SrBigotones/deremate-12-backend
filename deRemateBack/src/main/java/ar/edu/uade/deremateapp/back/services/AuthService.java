package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.dto.UsuarioDTO;
import ar.edu.uade.deremateapp.back.model.EstadoUsuario;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private EmailService emailService;

    public Usuario registrarPersona(Usuario usuario){
        usuario.setEstado(EstadoUsuario.PENDIENTE);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        String codigo = SecurityUtils.createRandomDigits();
//        service.saveEmailCode(user.getId(), code);
        emailService.enviarMensajeRegistroUsuario(usuario, codigo);

        return userRepository.save(usuario);
    }

    public Usuario confirmarRegistro(Usuario usuario) {
        return null;
    }
}
