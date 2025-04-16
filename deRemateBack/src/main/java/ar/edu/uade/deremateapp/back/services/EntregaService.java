package ar.edu.uade.deremateapp.back.services;


import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.EntregaRepository;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UserRepository userRepository;

    public List<EntregaDTO> getEntregasPorUsuario(Long usuarioId) {
        List<Entrega> entregas = entregaRepository.findByUsuarioId(usuarioId);
        return entregas.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public EntregaDTO crearEntrega(EntregaDTO dto) {
        Usuario usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Entrega entrega = new Entrega();
        entrega.setDireccion(dto.getDireccion());
        entrega.setEstado("Pendiente");
        entrega.setFecha(dto.getFecha());
        entrega.setObservaciones(dto.getObservaciones());
        entrega.setUsuario(usuario);

        Entrega guardada = entregaRepository.save(entrega);
        return convertirADTO(guardada);
    }

    public EntregaDTO actualizarEstado(Long entregaId, String nuevoEstado) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntregaNoEncontradaException("Entrega no encontrada"));

        entrega.setEstado(nuevoEstado);
        entregaRepository.save(entrega);
        return convertirADTO(entrega);
    }

    public EntregaDTO getEntregaPorId(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new EntregaNoEncontradaException("Entrega no encontrada"));

        return convertirADTO(entrega);
    }

    private EntregaDTO convertirADTO(Entrega entrega) {
        EntregaDTO dto = new EntregaDTO();
        dto.setId(entrega.getId());
        dto.setDireccion(entrega.getDireccion());
        dto.setEstado(entrega.getEstado());
        dto.setFecha(entrega.getFecha());
        dto.setObservaciones(entrega.getObservaciones());
        dto.setUsuarioId(entrega.getUsuario().getId());
        return dto;
    }
}
