package ar.edu.uade.deremateapp.back.exceptions;

public class MailAlreadyUsedException extends Exception {
    public MailAlreadyUsedException() {
        super("Ha ocurrido un error. El correo electrónico ya se encuentra utilizado por otro usuario.");
    }
}
