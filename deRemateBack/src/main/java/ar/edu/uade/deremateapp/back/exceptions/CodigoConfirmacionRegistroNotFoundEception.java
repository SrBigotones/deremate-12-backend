package ar.edu.uade.deremateapp.back.exceptions;

public class CodigoConfirmacionRegistroNotFoundEception extends Exception {
    public CodigoConfirmacionRegistroNotFoundEception(String email) {
        super("No existe un codigo de confirmacion para el email %s".formatted(email));
    }
}
