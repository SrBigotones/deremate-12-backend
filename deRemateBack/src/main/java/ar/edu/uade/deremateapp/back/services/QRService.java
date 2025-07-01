package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.dto.EntregaConQRDTO;
import ar.edu.uade.deremateapp.back.exceptions.CodigoQRInvalidoException;
import ar.edu.uade.deremateapp.back.model.Entrega;
import ar.edu.uade.deremateapp.back.model.EstadoEntrega;
import ar.edu.uade.deremateapp.back.repository.EntregaRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class QRService {

    @Autowired
    private EntregaService entregaService;

    /**
     * Genera un QR para una entrega específica
     * @param entregaId ID de la entrega
     * @return String con el QR en formato Base64
     */
    public String generarQRParaEntrega(Long entregaId) throws WriterException, IOException {
        String contenido = "ENTREGA_" + entregaId;
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(contenido, BarcodeFormat.QR_CODE, 200, 200);
        
        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }


    /**
     * Obtiene todas las entregas en estado PENDIENTE
     * @return Lista de entregas pendientes
     */
    public List<Entrega> getEntregasPendientes() {
        return entregaService.getEntregasPendientes();
    }

    /**
     * Obtiene una entrega por ID
     *
     * @param entregaId ID de la entrega
     * @return Optional con la entrega si existe
     *
     * public Optional<Entrega> getEntregaPorId(Long entregaId) {
     *         return entregaRepository.findById(entregaId);
     *     }
     */


    /**
     * Obtiene todas las entregas pendientes con sus QRs generados
     * @return Lista de entregas pendientes con QRs en Base64
     */
    public List<EntregaConQRDTO> getEntregasPendientesConQRs() {
        List<Entrega> entregasPendientes = getEntregasPendientes();
        List<EntregaConQRDTO> entregasConQR = new java.util.ArrayList<>();
        
        for (Entrega entrega : entregasPendientes) {
            try {
                String qrBase64 = generarQRParaEntrega(entrega.getId());
                EntregaConQRDTO dto = EntregaConQRDTO.fromEntrega(entrega);
                dto.setQrBase64(qrBase64);
                entregasConQR.add(dto);
            } catch (Exception e) {
                // Si hay error generando el QR, continuamos con las demás entregas
                System.err.println("Error generando QR para entrega " + entrega.getId() + ": " + e.getMessage());
            }
        }
        
        return entregasConQR;
    }
} 