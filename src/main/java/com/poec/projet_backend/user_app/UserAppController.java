package com.poec.projet_backend.user_app;

import com.poec.projet_backend.auth.AuthResponse;
import com.poec.projet_backend.domains.notification.NotificationController;
import com.poec.projet_backend.domains.notification.NotificationRepository;
import com.poec.projet_backend.domains.notification.NotificationService;
import com.poec.projet_backend.domains.sse.SseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAppController {

    private final UserAppRepository userAppRepository;
    private final NotificationService notificationService;
    private final SseService sseService;

    @GetMapping("/me")
    public ResponseEntity<?> getMe(HttpServletRequest request) {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        UserApp user = userAppRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("email " + username +" not found"));
        if(user.getTimeSinceLastCheckNotifications() == null){
            notificationService.resetUserApp(user.getId());
        }

        AuthResponse response = AuthResponse.builder()
                .message("Connection réussite")
                .id(user.getId())
                .role(user.getRole())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserApp> getUserByEmail(@PathVariable String email, HttpServletRequest request) throws AccessDeniedException {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (username.equals(email) || roles.equals("[ROLE_ADMIN]")) {
            return ResponseEntity.ok(userAppRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("email " + email +" not found"))
            );
        } else {
            throw new AccessDeniedException("UserApp does not have the correct rights to access to this resource");
        }
    }

    @GetMapping("/all")
    public List<UserApp> getAll(HttpServletRequest request) throws AccessDeniedException {
        String roles  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        System.out.println(roles);
        if(roles.equals("[ADMIN]")) {
            return userAppRepository.findAll();
        } else {
            throw new AccessDeniedException("UserApp does not have the correct rights to access to this resource");

        }
    }
}
