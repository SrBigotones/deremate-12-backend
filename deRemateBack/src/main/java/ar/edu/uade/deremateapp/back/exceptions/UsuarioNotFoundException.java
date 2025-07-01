package ar.edu.uade.deremateapp.back.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
  public UsuarioNotFoundException() {
    super("Usuario no encontrado");
  }
}
