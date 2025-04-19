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
import java.util.stream.Collectors;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EntregaDTO> getEntregasPorUsuario(Long usuarioId) {
        List<Entrega> entregas = entregaRepository.findByUsuarioId(usuarioId);
        return entregas.stream()
                .map(Entrega::convertirADTO)
                .collect(Collectors.toList());
    }

    public EntregaDTO crearEntrega(EntregaDTO dto) {
        Usuario usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Entrega entrega = new Entrega();
        entrega.setDireccionEntrega(dto.getDireccion());
        entrega.setEstado(EstadoEntrega.PENDIENTE);
        entrega.setFechaCreacion(LocalDateTime.now());
        entrega.setObservaciones(dto.getObservaciones());
        entrega.setUsuario(usuario);

        Entrega guardada = entregaRepository.save(entrega);
        return guardada.convertirADTO();
    }

    public EntregaDTO actualizarEstado(Long entregaId, EstadoEntrega nuevoEstado) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        entrega.setEstado(nuevoEstado);
        entrega = entregaRepository.save(entrega);
        return entrega.convertirADTO();
    }

    public EntregaDTO getEntregaPorId(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        return entrega.convertirADTO();
    }

    public List<Entrega> getEntregasPendientes(Long usuarioId){
        return entregaRepository.findByUsuarioIdAndEstado(usuarioId, EstadoEntrega.PENDIENTE);
    }


}
