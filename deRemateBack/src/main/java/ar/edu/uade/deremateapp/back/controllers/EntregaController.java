package ar.edu.uade.deremateapp.back.controllers;

import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import ar.edu.uade.deremateapp.back.dto.ErrorDTO;
import ar.edu.uade.deremateapp.back.dto.QRScanRequest;
import ar.edu.uade.deremateapp.back.exceptions.*;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.services.EntregaService;
import ar.edu.uade.deremateapp.back.services.UserService;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/entregas")
public class EntregaController {

    @Autowired
    private EntregaService entregaService;

    @Autowired
    private UserService usuarioService;

    
    @GetMapping("/mis-entregas")
    public ResponseEntity<List<EntregaDTO>> getMisEntregas(@RequestParam List<String> estados) throws UsuarioAutenticadoNoEncontradoException {
        Usuario user = SecurityUtils.getCurrentUser();

        List<EstadoEntrega> estadosEntrega = estados.stream().map(this::getEstadoEntrega).collect(Collectors.toList());

        // Si la lista de estados viene vacia, se usan todos los estados
        if (estadosEntrega.isEmpty()) {
            estadosEntrega = Arrays.asList(EstadoEntrega.values());
        }

        var entregas = entregaService.getEntregasPorUsuarioEnEstado(user.getId(), estadosEntrega);

        var entregasDto = entregas.stream().map(Entrega::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(entregasDto);
    }

    
    @PostMapping
    public ResponseEntity<EntregaDTO> crearEntrega(@RequestBody EntregaDTO entregaDto) throws Exception {
        return ResponseEntity.ok(entregaService.crearEntrega(entregaDto));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id
    ) {
        try {
            var response = entregaService.cancelar(id);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}/finalizar/{codigo}")
    public ResponseEntity<?> finalizarEntrega(
            @PathVariable Long id,
            @PathVariable String codigo
    ) {
        try {
            var response = entregaService.finalizarEntrega(id, codigo);
            return ResponseEntity.ok(response);
        } catch (CodigoConfirmacionEntregaInvalido e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("Codigo de confirmacion invalido"));
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/{id}/calificar")
    public ResponseEntity<?> actualizarCalificacion(@PathVariable Long id, @RequestBody EntregaDTO entregaDto) throws Exception {
        entregaDto.setId(id);
        entregaService.actualizarCalificacion(entregaDto);
        return ResponseEntity.ok("Calificado con exito");
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<EntregaDTO> getEntregaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entregaService.getEntregaPorId(id));
    }

    /**
     * Endpoint para escanear QR y cambiar estado de entrega
     * @param request Solicitud con el contenido del QR
     * @return ResponseEntity con el resultado
     */
    @PostMapping("/escanear-qr")
    public ResponseEntity<?> escanearQR(@RequestBody QRScanRequest request) throws EntregaNotFoundException {
        try {
            entregaService.transicionarAEnViaje(request.getContenidoQR());

            return ResponseEntity.ok("Entrega actualizada correctamente a estado EN_VIAJE");
        } catch (EntregaNotFoundException | IllegalStateException | CodigoQRInvalidoException e) {
            return ResponseEntity.badRequest().body("Error al procesar el QR. Verifique que la entrega esté en estado PENDIENTE");
        }
    }

    private EstadoEntrega getEstadoEntrega(String value) throws InvalidEstadoEntregaException {
        try {
            return EstadoEntrega.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidEstadoEntregaException(value);
        }
    }
}
