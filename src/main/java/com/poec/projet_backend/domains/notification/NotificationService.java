package com.poec.projet_backend.domains.notification;

import com.poec.projet_backend.domains.reservation.ReservationRepository;
import com.poec.projet_backend.domains.student.StudentFavoriteController;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@Data
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserAppRepository userRepository;
    private final ReservationRepository resRepository;
    private final StudentFavoriteController studentFavoriteController;

    public NotificationDTO createNotification(NotificationDTO dto) throws Exception {
        var user = userRepository.findById(dto.getUserId());
        if(user.isPresent()) {
            Notification notification = NotificationDTO.toNotification(dto,user.get());
            return NotificationDTO.fromNotification( notificationRepository.save(notification));
        }
        else throw  new Exception("User or reservation not found");
    }

    public List< NotificationDTO> getAll(Long userId) throws Exception {
        return notificationRepository.findAll().stream().map(NotificationDTO::fromNotification).toList();
    }

    public List<Map<String, Object>> getAll(Long userId, LocalDateTime time) throws Exception {
        return notificationRepository.getNotificationsSinceLastSeen(userId);
    }

    private final UserAppRepository userAppRepository;
    public UserApp resetUserApp(Long id) {
        LocalDateTime now = LocalDateTime.now();
        UserApp user = userAppRepository.findById(id).orElseThrow(()->new RuntimeException("User App Not Found"));
        user.setTimeSinceLastCheckNotifications(now);
        return userAppRepository.save(user);
    }
}
