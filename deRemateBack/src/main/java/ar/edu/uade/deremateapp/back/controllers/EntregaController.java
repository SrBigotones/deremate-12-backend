package ar.edu.uade.deremateapp.back.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ar.edu.uade.deremateapp.back.exceptions.InvalidEstadoEntregaException;
import ar.edu.uade.deremateapp.back.exceptions.UsuarioAutenticadoNoEncontradoException;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.uade.deremateapp.back.dto.EntregaDTO;
import ar.edu.uade.deremateapp.back.services.EntregaService;
import ar.edu.uade.deremateapp.back.services.UserService;

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

    
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoEntrega nuevoEstado
    ) {
        try {
            var response = entregaService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
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

    private EstadoEntrega getEstadoEntrega(String value) throws InvalidEstadoEntregaException {
        try {
            return EstadoEntrega.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidEstadoEntregaException(value);
        }

    }
}
