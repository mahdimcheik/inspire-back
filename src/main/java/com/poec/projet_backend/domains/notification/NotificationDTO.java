package com.poec.projet_backend.domains.notification;

import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.user_app.UserApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private NotificationType type;
    private Long userId;
    private LocalDateTime emittedAt;
    private String message;

    public static NotificationDTO fromNotification(Notification notification) {
        return NotificationDTO.builder()
                .type(notification.getType())
                .emittedAt(notification.getEmittedAt())
                .userId(notification.getUser().getId())
                .message(notification.getMessage())
                .build();
    }

    public static  Notification toNotification(NotificationDTO dto, UserApp user) {
        return Notification.builder()
                .emittedAt(dto.getEmittedAt())
                .type(dto.getType())
                .message(dto.getMessage())
                .user(user)
                .build();
    }

}
