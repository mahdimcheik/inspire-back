package com.poec.projet_backend.domains.mail;

import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MailDTO {
private Long id;
    private String title;
    private LocalDateTime sentDate;
    private String body;

    private Long senderId;
    private Long receiverId;

    private String senderFirstname;
    private String senderLastname;
    private String senderRole;
    private boolean isOpened = false;

    public static MailDTO fromEntity( final Mail mail) {
        return MailDTO.builder()
                .id(mail.getId())
                .title(mail.getTitle())
                .sentDate(mail.getSentDate())
                .body(mail.getBody())
                .senderId(mail.getSender().getId())
                .receiverId(mail.getReceiver().getId())
                .senderFirstname(mail.getSenderFirstName())
                .senderLastname(mail.getSenderLastName())
                .senderRole(mail.getSenderRole())
                .isOpened(mail.isOpened())
                .build();
    }
    public static Mail fromDTO( final Mail mail, final UserApp sender, final UserApp receiver) {
        return Mail.builder()
                .title(mail.getTitle())
                .sentDate(mail.getSentDate())
                .body(mail.getBody())
                .sender(sender)
                .receiver(receiver)
                .senderFirstName(mail.getSenderFirstName())
                .senderLastName(mail.getSenderLastName())
                .senderRole(mail.getSenderRole())
                .isOpened(mail.isOpened())
                .build();
    }
}
