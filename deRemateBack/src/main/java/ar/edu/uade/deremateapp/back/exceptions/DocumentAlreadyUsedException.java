package ar.edu.uade.deremateapp.back.exceptions;

public class DocumentAlreadyUsedException extends Exception {
    public DocumentAlreadyUsedException() {
        super("El documento ya se encuentra registrado. Intente recuperar su usuario.");
    }
}
