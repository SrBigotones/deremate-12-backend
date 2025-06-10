package ar.edu.uade.deremateapp.back.exceptions;

public class UsuarioInactivoException extends Exception {
    public UsuarioInactivoException() {
        super("El usuario no se encuentra activo");
    }
}
