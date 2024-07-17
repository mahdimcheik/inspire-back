package com.poec.projet_backend.domains.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private LocalDateTime sentDate;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;

    private String senderFirstName;
    private String senderLastName;
    private String senderRole;
    private String imgUrl;
    private boolean isOpened = false;

    @ManyToOne
    @JoinColumn(name = "senderId")
    @JsonIgnore
    private UserApp sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    @JsonIgnore
    private UserApp receiver;
}
