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
import java.util.ArrayList;
import java.util.List;

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

    private boolean isOpened = false;

    private String senderFirstname;
    private String senderLastname;
    private String senderRole;

    private String receiverFirstname;
    private String receiverLastname;
    private String receiverRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId", nullable = false)
    @JsonIgnore
    private UserApp sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiverId", nullable = false)
    @JsonIgnore
    private UserApp receiver;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "responseToId")
//    @JsonIgnore
//    private Mail responseToMail;
}
