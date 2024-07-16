package com.poec.projet_backend.domains.mail;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MailSend {
    private String title;
    private String body;
    private Long receiverId;
}
