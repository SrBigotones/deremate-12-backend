package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.CodigoConfirmacionRegistro;
import ar.edu.uade.deremateapp.back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoConfirmacionRegistroRepository extends JpaRepository<CodigoConfirmacionRegistro, Long> {

    Optional<CodigoConfirmacionRegistro> findByUsuario(Usuario usuario);

    Optional<CodigoConfirmacionRegistro> findByCodigoAndUsuarioId(String codigo, Long usuarioId);
}