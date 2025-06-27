package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.EntregaRepository;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Entrega> getEntregasPorUsuarioEnEstado(Long usuarioId, List<EstadoEntrega> estados) {
        return entregaRepository.findByUsuarioIdAndEstadoIn(usuarioId, estados);
    }

    public EntregaDTO crearEntrega(EntregaDTO dto) {
        Usuario usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Entrega entrega = new Entrega();
        entrega.setDireccionEntrega(dto.getDireccion());

        // Por defecto, las nuevas entregas son PENDIENTES (esto está bien)
        entrega.setEstado(EstadoEntrega.PENDIENTE);
        entrega.setFechaCreacion(LocalDateTime.now());
        entrega.setObservaciones(dto.getObservaciones());
        entrega.setUsuario(usuario);

        // Validar que solo pueda calificar, comentar o subir imagen si está ENTREGADO
        if (dto.getCalificacion() != null || dto.getComentario() != null || dto.getImagen() != null) {
            if (dto.getEstado() != EstadoEntrega.ENTREGADO) {
                throw new IllegalStateException("Solo se puede asignar calificación, comentario o imagen si la entrega está ENTREGADA.");
            }
        }


        entrega.setCalificacion(dto.getCalificacion());
        entrega.setComentario(dto.getComentario());

        Entrega guardada = entregaRepository.save(entrega);
        return guardada.convertirADTO();
    }



    public EntregaDTO actualizarEstado(Long entregaId, EstadoEntrega nuevoEstado) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        EstadoEntrega estadoActual = entrega.getEstado();

        // Validar transición de estado
        if (!esTransicionValida(estadoActual, nuevoEstado)) {
            throw new IllegalStateException("Transición de estado no permitida: " +
                    estadoActual + " → " + nuevoEstado);
        }

        entrega.setEstado(nuevoEstado);

        // Si el estado es ENTREGADO, registrar la fecha de entrega
        if (nuevoEstado == EstadoEntrega.ENTREGADO) {
            entrega.setFechaEntrega(LocalDateTime.now());
        }

        entrega = entregaRepository.save(entrega);
        return entrega.convertirADTO();
    }


    public EntregaDTO getEntregaPorId(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        return entrega.convertirADTO();
    }

    private boolean esTransicionValida(EstadoEntrega estadoActual, EstadoEntrega nuevoEstado) {
        // Si el nuevo estado es CANCELADO y el estado actual no es ENTREGADO
        if (nuevoEstado == EstadoEntrega.CANCELADO && estadoActual != EstadoEntrega.ENTREGADO) {
            return true;
        }

        // Verificar transiciones permitidas
        return switch (estadoActual) {
            case SIN_ASIGNAR -> nuevoEstado == EstadoEntrega.PENDIENTE;
            case PENDIENTE -> nuevoEstado == EstadoEntrega.EN_VIAJE;
            case EN_VIAJE -> nuevoEstado == EstadoEntrega.ENTREGADO;
            default -> false;
        };
    }
}
