package ar.edu.uade.deremateapp.back.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "push_tokens")
public class PushToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expo_token", nullable = false, unique = true)
    private String expoToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario user;

    @Column(name = "device_name") // optional info
    private String deviceName;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructors, Getters, Setters
}