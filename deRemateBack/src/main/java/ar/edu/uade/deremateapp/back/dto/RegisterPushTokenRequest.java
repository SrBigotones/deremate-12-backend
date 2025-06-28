package ar.edu.uade.deremateapp.back.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor
public class RegisterPushTokenRequest {
    private String expoToken;
    private String deviceName;
}