package com.poec.projet_backend.domains.notification;

import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private NotificationType type;
    private LocalDateTime emittedAt;
    private String message;

    @ManyToOne
    @JoinColumn(name = "userId")
    UserApp user;

}
