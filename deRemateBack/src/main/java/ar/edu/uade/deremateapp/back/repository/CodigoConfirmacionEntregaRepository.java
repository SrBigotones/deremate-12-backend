package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.CodigoConfirmacionEntrega;
import ar.edu.uade.deremateapp.back.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CodigoConfirmacionEntregaRepository extends JpaRepository<CodigoConfirmacionEntrega, Long> {

    Optional<CodigoConfirmacionEntrega> findByEntrega(Entrega entrega);

    Optional<CodigoConfirmacionEntrega> findByCodigoAndEntregaId(String codigo, Long entregaId);
}