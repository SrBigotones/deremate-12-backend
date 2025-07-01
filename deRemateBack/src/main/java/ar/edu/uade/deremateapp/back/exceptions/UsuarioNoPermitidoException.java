package ar.edu.uade.deremateapp.back.exceptions;

public class UsuarioNoPermitidoException extends RuntimeException {
    public UsuarioNoPermitidoException() {
        super("Error al procesar la entrega. La misma corresponde a otro usuario.");
    }
}
