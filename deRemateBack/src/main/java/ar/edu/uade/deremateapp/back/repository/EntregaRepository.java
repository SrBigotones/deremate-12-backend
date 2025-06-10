package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    List<Entrega> findByUsuarioId(Long usuarioId);
    List<Entrega> findByEstado(EstadoEntrega estado);
    List<Entrega> findByUsuarioIdAndEstadoNotIn(Long usuarioId, List<EstadoEntrega> estados);
    List<Entrega> findByUsuarioIdAndEstadoIn(Long usuarioId, List<EstadoEntrega> estados);
}
