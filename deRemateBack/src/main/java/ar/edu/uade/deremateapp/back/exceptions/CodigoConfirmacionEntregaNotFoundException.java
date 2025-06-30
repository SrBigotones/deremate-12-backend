package ar.edu.uade.deremateapp.back.exceptions;

public class CodigoConfirmacionEntregaNotFoundException extends Exception {
        public CodigoConfirmacionEntregaNotFoundException(Long entregaId) {
                super("No existe un codigo de confirmacion para la entrega %s".formatted(entregaId));
        }
}
