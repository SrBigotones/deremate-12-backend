package ar.edu.uade.deremateapp.back.exceptions;

public class UsuarioConEmailNotFoundException extends Exception {
    public UsuarioConEmailNotFoundException(String email) {
        super("El usuario con el email %s no existe".formatted(email));
    }
}
