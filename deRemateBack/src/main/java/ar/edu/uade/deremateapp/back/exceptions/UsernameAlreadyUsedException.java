package ar.edu.uade.deremateapp.back.exceptions;

public class UsernameAlreadyUsedException extends Exception {
    public UsernameAlreadyUsedException() {
        super("El usuario ya se encuentra utilizado. Por favor, intente nuevamente con otro usuario.");
    }
}
