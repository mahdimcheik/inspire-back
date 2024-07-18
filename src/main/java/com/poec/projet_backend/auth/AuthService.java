package com.poec.projet_backend.auth;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.notification.NotificationService;
import com.poec.projet_backend.domains.sse.SseService;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.exceptions.UsernameAlreadyTakenException;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserAppService;
import com.poec.projet_backend.util.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserAppRepository repository;
    private final MentorRepository mentorRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NotificationService notificationService;
    private final SseService sseService;

    public Map<String, String> registerMentor(RegisterRequest request, HttpServletRequest httpRequest) throws UsernameAlreadyTakenException {

        if (!repository.findByEmail(request.getEmail()).isPresent()) {
            var user = UserApp.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.MENTOR.name())
                    .createdAt(LocalDateTime.now())
                    .build();

            repository.save(user);
            var mentor = Mentor.builder()
                    .user(user)
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .build();

            mentorRepository.save(mentor);


            Map<String, String> body = new HashMap<>();
            body.put("message", "Account successfully created as user and mentor");
            return body;

        } else {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

    }

    public Map<String, String> registerStudent(RegisterRequest request, HttpServletRequest httpRequest) throws UsernameAlreadyTakenException {

        if (!repository.findByEmail(request.getEmail()).isPresent()) {
            var user = UserApp.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.STUDENT.name())
                    .createdAt(LocalDateTime.now())
                    .build();

            repository.save(user);

            var student = Student.builder()
                    .user(user)
                    .firstname(request.getFirstname())
                    .lastname(request.getLastname())
                    .build();

            studentRepository.save(student);


            Map<String, String> body = new HashMap<>();
            body.put("message", "Account successfully created as user and student");
            return body;

        } else {
            throw new UsernameAlreadyTakenException("Username already taken");
        }

    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request, HttpServletRequest httpRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            UserApp user = repository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found in DB"));

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.getRole());

            String jwtToken = jwtService.generateToken(new HashMap<>(extraClaims), user);
            if(user.getTimeSinceLastCheckNotifications() == null){
                notificationService.resetUserApp(user.getId());
            }

            return AuthResponse.builder()
                    .token(jwtToken)
                    .message("Logged In")
                    .role(user.getRole())
                    .id(user.getId())
                    .build();
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Bad credentials");
        }

    }
}
