package ar.edu.uade.deremateapp.back.repository;

import ar.edu.uade.deremateapp.back.model.PushToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PushTokenRepository extends JpaRepository<PushToken, Long> {
    Optional<PushToken> findByExpoToken(String expoToken);
    List<PushToken> findByUserId(Long userId);
}
