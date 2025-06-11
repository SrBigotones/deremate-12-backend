package ar.edu.uade.deremateapp.back.services;


import ar.edu.uade.deremateapp.back.dto.UsuarioDTO;
import ar.edu.uade.deremateapp.back.model.EstadoUsuario;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    public UsuarioDTO obtenerUsuario(Long id)
    {
        Usuario user = userRepository.findById(id).get();
        return user.toUsuarioDTO();
    }

    public Optional<Usuario> buscarUsuarioPorMail(String mail) {
        return userRepository.findByEmail(mail);
    }

    public void confirmarRegistro(Usuario usuario) {
        usuario.setEstado(EstadoUsuario.ACTIVO);
        userRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Usuario> buscarUsuarioPorDNI(int documento) {
        return userRepository.findByDocumento(documento);
    }
}
