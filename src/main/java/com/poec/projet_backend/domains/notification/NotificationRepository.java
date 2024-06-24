package com.poec.projet_backend.domains.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Query(value = "SELECT n.* FROM notification n " +
            "JOIN user u ON n.userId = u.id " +
            "WHERE  n.userId = :userId "+
            "LIMIT 10"
            ,
            nativeQuery = true)
    List<Map<String, Object>> getNotificationsSinceLastSeen(@Param("userId") Long userId);
}


//public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    @Query(value = "SELECT n.* FROM notification n " +
//            "JOIN user u ON n.userId = u.id " +
//            "WHERE u.timeSinceLastCheckNotifications < n.emittedAt AND n.userId = :userId",
//            nativeQuery = true)
//    List<Map<String, Object>> getNotificationsSinceLastSeen(@Param("userId") Long userId);
//}