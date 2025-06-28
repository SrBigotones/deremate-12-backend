package ar.edu.uade.deremateapp.back.services;

import ar.edu.uade.deremateapp.back.model.PushToken;
import ar.edu.uade.deremateapp.back.repository.PushTokenRepository;
import ar.edu.uade.deremateapp.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PushTokenService {

    @Autowired
    private PushTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;


    private static final String EXPO_API_URL = "https://exp.host/--/api/v2/push/send";

    public void registerToken(Long userId, String expoToken, String deviceName) {
        PushToken token = tokenRepository.findByExpoToken(expoToken)
                .orElseGet(() -> {
                    PushToken newToken = new PushToken();
                    newToken.setExpoToken(expoToken);
                    newToken.setUser(userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found")));
                    return newToken;
                });

        token.setDeviceName(deviceName);
        token.setLastUpdated(LocalDateTime.now());
        tokenRepository.save(token);
    }

    public void sendNotification(Long userId, String title, String message) throws Exception {
        URL url = new URL(EXPO_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        List<PushToken> pushTokens = tokenRepository.findByUserId(userId);

        for (PushToken pushToken : pushTokens) {

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String payload = """
            [{
              "to": "%s",
              "sound": "default",
              "title": "%s",
              "body": "%s"
            }]
            """.formatted(pushToken.getExpoToken(), title, message);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Expo response code: " + responseCode);

            if (responseCode != 200) {
                throw new RuntimeException("Failed to send push notification. HTTP status: " + responseCode);
            }
        }

    }


    public void triggerManually(String expoToken) throws IOException {
        URL url = new URL(EXPO_API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String payload = """
        [{
          "to": "%s",
          "sound": "default",
          "title": "%s",
          "body": "%s"
        }]
        """.formatted(expoToken, "test-notification-service", "this is working correctly");

        try (OutputStream os = conn.getOutputStream()) {
            os.write(payload.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        System.out.println("Expo response code: " + responseCode);

        if (responseCode != 200) {
            throw new RuntimeException("Failed to send push notification. HTTP status: " + responseCode);
        }
    }
}