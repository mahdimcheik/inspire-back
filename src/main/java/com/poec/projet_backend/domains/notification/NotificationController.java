package com.poec.projet_backend.domains.notification;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @GetMapping("/get/all/{userId}")
    public ResponseEntity<List<NotificationDTO>> getAll(@PathVariable Long userId)  {
        try {
            return new ResponseEntity<>( notificationService.getAll(userId), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUserNotifications(@PathVariable Long userId)  {
        try {
            return new ResponseEntity<>( notificationService.getAll(userId, LocalDateTime.now()), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<NotificationDTO> getAll(@RequestBody NotificationDTO notificationDTO)  {
        try {
            return new ResponseEntity<>( notificationService.createNotification(notificationDTO), HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reset/{userId}")
    public ResponseEntity<List<NotificationDTO>> resetNotification(@PathVariable Long userId)  {
        try {
            notificationService.resetUserApp(userId);
            return ResponseEntity.ok().body(notificationService.getAll(userId));
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
}
