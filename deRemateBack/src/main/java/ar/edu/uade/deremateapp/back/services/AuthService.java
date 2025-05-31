package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.exceptions.CodigoConfirmacionRegistroNotFoundEception;
import ar.edu.uade.deremateapp.back.model.CodigoConfirmacionRegistro;
import ar.edu.uade.deremateapp.back.model.EstadoUsuario;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.CodigoConfirmacionRegistroRepository;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired private UserRepository userRepository;

    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private EmailService emailService;

    @Autowired private CodigoConfirmacionRegistroRepository codigoConfirmacionRegistroRepository;

    @Transactional
    public Usuario registrarPersona(Usuario usuario) {
        usuario.setEstado(EstadoUsuario.PENDIENTE);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario nuevoUsuario = userRepository.save(usuario);

        String codigo = SecurityUtils.createRandomDigits();
        guardarCodigo(nuevoUsuario, codigo);
        emailService.enviarMensajeRegistroUsuario(usuario, codigo);

        return nuevoUsuario;
    }

    @Transactional
    public Usuario updatePassword(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return userRepository.save(usuario);
    }

    @Transactional
    public Usuario passwordRecovery(Usuario usuario) {

        String codigo = SecurityUtils.createRandomDigits();
        guardarCodigo(usuario, codigo);
        emailService.enviarMensajeOlvidoPassword(usuario, codigo);

        return usuario;
    }

    @Transactional
    public void guardarCodigo(Usuario usuario, String codigo) {
        Optional<CodigoConfirmacionRegistro> optCodigo = codigoConfirmacionRegistroRepository.findById(usuario.getId());

        CodigoConfirmacionRegistro nuevoCodigo;
        if (optCodigo.isPresent()) {
            nuevoCodigo = optCodigo.get();
            nuevoCodigo.setCodigo(codigo);
            nuevoCodigo.setFechaGeneracion(LocalDateTime.now());
        } else {
            nuevoCodigo = new CodigoConfirmacionRegistro(usuario, codigo);
        }

        codigoConfirmacionRegistroRepository.save(nuevoCodigo);
    }

    public boolean esCodigoDeRegistroValido(Usuario usuario, String codigo) throws CodigoConfirmacionRegistroNotFoundEception {
        var codigoConfirmacion = codigoConfirmacionRegistroRepository
                .findByUsuario(usuario).orElseThrow(() -> new CodigoConfirmacionRegistroNotFoundEception(usuario.getEmail()));

        return codigoConfirmacion.getCodigo().equals(codigo);
    }
}
