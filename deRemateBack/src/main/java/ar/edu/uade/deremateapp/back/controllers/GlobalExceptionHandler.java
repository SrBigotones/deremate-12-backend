package ar.edu.uade.deremateapp.back.controllers;

import ar.edu.uade.deremateapp.back.dto.ErrorDTO;
import ar.edu.uade.deremateapp.back.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioConEmailNotFoundException.class)
    public ResponseEntity<?> usuarioConEmailNotFound(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailAlreadyUsedException.class)
    public ResponseEntity<?> mailAlreadyUsedException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioInactivoException.class)
    public ResponseEntity<?> usuarioInactivoException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsuarioAutenticadoNoEncontradoException.class)
    public ResponseEntity<?> usuarioAutenticadoNoEncontradoException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PushNotificationSendException.class)
    public ResponseEntity<?> pushNotificationSendException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<?> usuarioNotFoundException(Exception ex, WebRequest request) {
        logError(ex);
        return new ResponseEntity<>(new ErrorDTO(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private void logError(Exception e) {
        e.printStackTrace();
    }
}
