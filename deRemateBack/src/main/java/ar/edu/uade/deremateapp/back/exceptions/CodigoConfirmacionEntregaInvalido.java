package ar.edu.uade.deremateapp.back.exceptions;

public class CodigoConfirmacionEntregaInvalido extends Exception {
    public CodigoConfirmacionEntregaInvalido() {
        super("El codigo de confirmacion provisto es invalido");
    }
}
