package ar.edu.uade.deremateapp.back.exceptions;

public class CodigoQRInvalidoException extends Exception {
    public CodigoQRInvalidoException() {
        super("El QR no es valido");
    }
}
