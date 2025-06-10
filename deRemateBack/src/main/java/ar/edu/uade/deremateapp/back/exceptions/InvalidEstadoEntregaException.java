package ar.edu.uade.deremateapp.back.exceptions;

import ar.edu.uade.deremateapp.back.model.EstadoEntrega;

import java.util.Arrays;

public class InvalidEstadoEntregaException extends RuntimeException {
    public InvalidEstadoEntregaException(String value) {
        super("El estado %s no es valido, los estados validos son %s".formatted(value, Arrays.toString(EstadoEntrega.values())));
    }
}
