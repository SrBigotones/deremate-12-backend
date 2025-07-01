package ar.edu.uade.deremateapp.back.controllers;

import ar.edu.uade.deremateapp.back.dto.EntregaConQRDTO;
import ar.edu.uade.deremateapp.back.dto.QRScanRequest;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.services.QRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private QRService qrService;

    /**
     * Interfaz web para generar QRs de entregas pendientes
     * @param model Modelo de Thymeleaf
     * @return Vista con los QRs
     */
    @GetMapping("/generar")
    public String generarQRsPendientes(Model model) {
        List<Entrega> entregasPendientes = qrService.getEntregasPendientes();
        model.addAttribute("entregas", entregasPendientes);
        return "qr-generator";
    }

    /**
     * Genera un QR específico para una entrega
     * @param entregaId ID de la entrega
     * @return ResponseEntity con el QR en Base64
     */
    @GetMapping("/generar/{entregaId}")
    @ResponseBody
    public ResponseEntity<?> generarQRParaEntrega(@PathVariable Long entregaId) {
        try {
            String qrBase64 = qrService.generarQRParaEntrega(entregaId);
            return ResponseEntity.ok(qrBase64);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al generar el QR: " + e.getMessage());
        }
    }

    /**
     * Obtiene información de una entrega específica
     * @param entregaId ID de la entrega
     * @return ResponseEntity con la información de la entrega
     */
    @GetMapping("/entrega/{entregaId}")
    @ResponseBody
    public ResponseEntity<?> getEntregaInfo(@PathVariable Long entregaId) {
        return qrService.getEntregaPorId(entregaId)
                .map(entrega -> ResponseEntity.ok(entrega))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Obtiene las entregas pendientes como JSON para actualización dinámica
     * @return ResponseEntity con la lista de entregas pendientes con QRs
     */
    @GetMapping("/entregas-pendientes")
    @ResponseBody
    public ResponseEntity<List<EntregaConQRDTO>> getEntregasPendientes() {
        List<EntregaConQRDTO> entregasPendientes = qrService.getEntregasPendientesConQRs();
        return ResponseEntity.ok(entregasPendientes);
    }
} 