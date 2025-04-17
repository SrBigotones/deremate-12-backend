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
    private UserService usuarioService;

    
    @GetMapping("/mis-entregas")
    public ResponseEntity<List<EntregaDTO>> getMisEntregas() {
        
        Long usuarioId = usuarioService.obtenerUsuario(1L).getId(); 
        return ResponseEntity.ok(entregaService.getEntregasPorUsuario(usuarioId));
    }

    
    @PostMapping
    public ResponseEntity<EntregaDTO> crearEntrega(@RequestBody EntregaDTO entregaDto) {
        return ResponseEntity.ok(entregaService.crearEntrega(entregaDto));
    }

    
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntregaDTO> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String nuevoEstado
    ) {
        return ResponseEntity.ok(entregaService.actualizarEstado(id, nuevoEstado));
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<EntregaDTO> getEntregaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(entregaService.getEntregaPorId(id));
    }
}
