package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Usuario findByUsername(String username);

}
