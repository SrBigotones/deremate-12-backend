package ar.edu.uade.deremateapp.back.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService usuarioService; // si necesitas obtener el usuario actual desde el token

    // 1. Obtener todas las entregas del usuario actual
    @GetMapping("/mis-entregas")
    public ResponseEntity<List<EntregaDTO>> getMisEntregas() {
        // obtenés el ID del usuario logueado desde el token
        Long usuarioId = usuarioService.obtenerUsuario(Long id);
        return ResponseEntity.ok(entregaService.getEntregasPorUsuario(usuarioId));
    }

    // 2. Crear una nueva entrega
    @PostMapping
    public ResponseEntity<EntregaDTO> crearEntrega(@RequestBody EntregaDTO entregaDto) {
        return ResponseEntity.ok(entregaService.crearEntrega(entregaDto));
    }

    // 3. Cambiar el estado de una entrega
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntregaDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok(entregaService.actualizarEstado(id, nuevoEstado));
    }

    // 4. Ver detalle de una entrega
    @GetMapping("/{id}")
    public ResponseEntity<EntregaDTO> getEntregaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entregaService.getEntregaPorId(id));
    }
}
