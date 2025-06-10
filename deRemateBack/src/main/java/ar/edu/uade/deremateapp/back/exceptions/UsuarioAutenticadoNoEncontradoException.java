package ar.edu.uade.deremateapp.back.exceptions;

public class UsuarioAutenticadoNoEncontradoException extends Exception {
    public UsuarioAutenticadoNoEncontradoException() {
        super("El usuario autenticado no se encontro o falta contexto de autenticacion");
    }
}
