package ar.edu.uade.deremateapp.back.services;

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
    private EntregaRepository entregaRepository;

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
     * Procesa el escaneo de un QR
     * @param contenidoQR Contenido del QR escaneado
     * @return devuelve el id de entrega
     */
    public Long procesarEscaneoQR(String contenidoQR) throws CodigoQRInvalidoException {
        // El contenido del QR debe tener el formato "ENTREGA_ID"
        if (!contenidoQR.startsWith("ENTREGA_")) {
            throw new CodigoQRInvalidoException();
        }

        return Long.parseLong(contenidoQR.substring(8));
    }

    /**
     * Obtiene todas las entregas en estado PENDIENTE
     * @return Lista de entregas pendientes
     */
    public List<Entrega> getEntregasPendientes() {
        return entregaRepository.findByEstado(EstadoEntrega.PENDIENTE);
    }

    /**
     * Obtiene una entrega por ID
     * @param entregaId ID de la entrega
     * @return Optional con la entrega si existe
     */
    public Optional<Entrega> getEntregaPorId(Long entregaId) {
        return entregaRepository.findById(entregaId);
    }
} 