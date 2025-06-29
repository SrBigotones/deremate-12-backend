package ar.edu.uade.deremateapp.back.controllers;

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
     * Endpoint para procesar el escaneo de un QR desde el frontend
     * @param request Solicitud con el contenido del QR escaneado
     * @return ResponseEntity con el resultado del procesamiento
    Deprecado, se usa en EntregaController
    @PostMapping("/escanear")
    @ResponseBody
    public ResponseEntity<?> escanearQR(@RequestBody QRScanRequest request) {
        boolean procesado = qrService.procesarEscaneoQR(request.getContenidoQR());

        if (procesado) {
            return ResponseEntity.ok("QR procesado correctamente. Estado de entrega actualizado a EN_VIAJE");
        } else {
            return ResponseEntity.badRequest().body("Error al procesar el QR. Verifique que la entrega esté en estado PENDIENTE");
        }
    }
     */

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
} 