package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import ar.edu.uade.deremateapp.back.exceptions.CodigoConfirmacionEntregaInvalido;
import ar.edu.uade.deremateapp.back.exceptions.CodigoConfirmacionEntregaNotFoundException;
import ar.edu.uade.deremateapp.back.exceptions.CodigoQRInvalidoException;
import ar.edu.uade.deremateapp.back.exceptions.EntregaNotFoundException;
import ar.edu.uade.deremateapp.back.model.CodigoConfirmacionEntrega;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.repository.CodigoConfirmacionEntregaRepository;
import ar.edu.uade.deremateapp.back.repository.EntregaRepository;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PushTokenService pushTokenService;

    @Autowired
    private QRService qrService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodigoConfirmacionEntregaRepository codigoConfirmacionEntregaRepository;

    public List<Entrega> getEntregasPorUsuarioEnEstado(Long usuarioId, List<EstadoEntrega> estados) {
        return entregaRepository.findByUsuarioIdAndEstadoIn(usuarioId, estados);
    }

    public EntregaDTO crearEntrega(EntregaDTO dto) throws Exception {
        Usuario usuario = userRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Entrega entrega = new Entrega();
        entrega.setDireccionEntrega(dto.getDireccion());

        entrega.setEstado(EstadoEntrega.PENDIENTE);
        entrega.setFechaCreacion(LocalDateTime.now());
        entrega.setObservaciones(dto.getObservaciones());
        entrega.setUsuario(usuario);
        entrega.setCalificacion(0);
        entrega.setComentario("");
        entrega.setImagen("");

        var guardada = entregaRepository.save(entrega);


        return guardada.convertirADTO();
    }

    public void transicionarAEnViaje(String codigoQR) throws EntregaNotFoundException, CodigoQRInvalidoException {
        Long entregaId = qrService.procesarEscaneoQR(codigoQR);

        Optional<Entrega> entregaOpt = entregaRepository.findById(entregaId);

        if (entregaOpt.isEmpty()) {
            throw new EntregaNotFoundException();
        }

        Entrega entrega = entregaOpt.get();
        EstadoEntrega estadoActual = entrega.getEstado();

        // Solo cambiar estado si está PENDIENTE
        if (!EstadoEntrega.PENDIENTE.equals(estadoActual)) {
            throw new IllegalStateException("Transición de estado no permitida: " +
                    estadoActual + " → " + EstadoEntrega.CANCELADO);
        }

        String codigo = SecurityUtils.createRandomDigits();
        guardarCodigoConfirmacion(entrega, codigo);

        emailService.enviarMensajeCodigoConfirmacion(entrega, codigo);

        entrega.setEstado(EstadoEntrega.EN_VIAJE);
        entregaRepository.save(entrega);
    }

    public EntregaDTO cancelar(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        EstadoEntrega estadoActual = entrega.getEstado();

        // Validar transición de estado
        if (!esTransicionValida(estadoActual, EstadoEntrega.CANCELADO)) {
            throw new IllegalStateException("Transición de estado no permitida: " +
                    estadoActual + " → " + EstadoEntrega.CANCELADO);
        }

        entrega.setEstado(EstadoEntrega.CANCELADO);
        entrega = entregaRepository.save(entrega);

        return entrega.convertirADTO();
    }

    public EntregaDTO finalizarEntrega(Long entregaId, String codigo) throws CodigoConfirmacionEntregaNotFoundException, CodigoConfirmacionEntregaInvalido {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        EstadoEntrega estadoActual = entrega.getEstado();

        // Validar transición de estado
        if (!EstadoEntrega.EN_VIAJE.equals(estadoActual)) {
            throw new IllegalStateException("Transición de estado no permitida: " +
                    estadoActual + " → " + EstadoEntrega.ENTREGADO);
        }

        if (!esCodigoDeConfirmacionValido(entrega, codigo)) {
            throw new CodigoConfirmacionEntregaInvalido();
        }

        entrega.setEstado(EstadoEntrega.ENTREGADO);
        entrega.setFechaEntrega(LocalDateTime.now());
        entrega = entregaRepository.save(entrega);

        return entrega.convertirADTO();
    }

    public void actualizarCalificacion(EntregaDTO dto) throws Exception {
        Entrega entrega = entregaRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        if (entrega.getEstado() != EstadoEntrega.ENTREGADO) {
            throw new Exception("error: solo se pueden calificar entregas con el estado ENTREGADO");
        }

        entrega.setCalificacion(dto.getCalificacion());
        entrega.setComentario(dto.getComentario());

        entregaRepository.save(entrega);
        pushTokenService.sendNotification(entrega.getUsuario().getId(), "Nueva calificacion", "Te han calificado en una entrega");
    }


    public EntregaDTO getEntregaPorId(Long entregaId) {
        Entrega entrega = entregaRepository.findById(entregaId)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada"));

        return entrega.convertirADTO();
    }

    private boolean esTransicionValida(EstadoEntrega estadoActual, EstadoEntrega nuevoEstado) {
        // Verificar transiciones permitidas
        return switch (estadoActual) {
            case PENDIENTE -> Arrays.asList(EstadoEntrega.EN_VIAJE, EstadoEntrega.CANCELADO).contains(nuevoEstado);
            case EN_VIAJE -> Arrays.asList(EstadoEntrega.ENTREGADO, EstadoEntrega.CANCELADO).contains(nuevoEstado);
            default -> false;
        };
    }

    private boolean esCodigoDeConfirmacionValido(Entrega entrega, String codigo) throws CodigoConfirmacionEntregaNotFoundException {
        var codigoConfirmacion =
                codigoConfirmacionEntregaRepository.findByEntrega(entrega).orElseThrow(() -> new CodigoConfirmacionEntregaNotFoundException(entrega.getId()));

        return codigoConfirmacion.getCodigo().equals(codigo);
    }

    @Transactional
    public void guardarCodigoConfirmacion(Entrega entrega, String codigo) {
        Optional<CodigoConfirmacionEntrega> optCodigo = codigoConfirmacionEntregaRepository.findById(entrega.getId());

        CodigoConfirmacionEntrega nuevoCodigo;
        if (optCodigo.isPresent()) {
            nuevoCodigo = optCodigo.get();
            nuevoCodigo.setCodigo(codigo);
            nuevoCodigo.setFechaGeneracion(LocalDateTime.now());
        } else {
            nuevoCodigo = new CodigoConfirmacionEntrega(entrega, codigo);
        }

        codigoConfirmacionEntregaRepository.save(nuevoCodigo);
    }
}
