package ar.edu.uade.deremateapp.back.controllers;

import ar.edu.uade.deremateapp.back.dto.RegisterPushTokenRequest;
import ar.edu.uade.deremateapp.back.exceptions.UsuarioAutenticadoNoEncontradoException;
import ar.edu.uade.deremateapp.back.model.Usuario;
import ar.edu.uade.deremateapp.back.services.PushTokenService;
import ar.edu.uade.deremateapp.back.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/notifications")
public class PushTokenController {

    @Autowired
    private PushTokenService pushTokenService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterPushTokenRequest request) throws UsuarioAutenticadoNoEncontradoException {
        Usuario user = SecurityUtils.getCurrentUser();
        System.out.println(request);

        pushTokenService.registerToken(
                user.getId(),
                request.getExpoToken(),
                request.getDeviceName()
        );
        return ResponseEntity.ok().build();
    }


    @PostMapping("/trigger-manually")
    public ResponseEntity<Void> triggerManually(@RequestBody RegisterPushTokenRequest request) throws UsuarioAutenticadoNoEncontradoException, IOException {

        pushTokenService.triggerManually(request.getExpoToken());

        return ResponseEntity.ok().build();
    }
}
