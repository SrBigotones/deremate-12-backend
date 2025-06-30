package ar.edu.uade.deremateapp.back.exceptions;

public class EntregaNotFoundException extends Exception {
    public EntregaNotFoundException() {
        super("La entrega no fue encontrada");
    }
}
