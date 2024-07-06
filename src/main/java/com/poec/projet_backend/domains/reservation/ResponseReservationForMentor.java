package com.poec.projet_backend.domains.reservation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseReservationForMentor {
    private Long id;
    private Long userId;
    private Long mentorUserId;
    private Long studentId;
    private Long slotId;
    private Long reservationId;
    private boolean isVisio;
    private String subject;
    private String message;
    private String firstname;
    private String lastname;
    private String title;
    private String imgUrl;
    private String details;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
}
