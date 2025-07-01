package ar.edu.uade.deremateapp.back.exceptions;

public class PushNotificationSendException extends RuntimeException {
    public PushNotificationSendException() {
        super("La notification no se pudo enviar");
    }
}
