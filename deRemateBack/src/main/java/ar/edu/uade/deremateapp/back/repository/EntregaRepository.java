package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByUsuarioId(Long usuarioId);
}
