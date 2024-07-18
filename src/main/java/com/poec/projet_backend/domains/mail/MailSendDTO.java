package com.poec.projet_backend.domains.mail;



import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Builder
public class MailSendDTO {
    private String title;
    private LocalDateTime sentDate;
    private String body;

    private boolean isOpened = false;

    private Long senderId;
    private Long receiverId;
    private Long responseToId;


}
