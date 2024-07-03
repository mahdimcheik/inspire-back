package com.poec.projet_backend.domains.slot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SlotResponseForMentorDTO {
    private Long id;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private boolean visio;
    private Long reservationId;
    private String firstname;
    private String lastname;
    private String imgUrl;
    private String message;
    private String details;
    private String Subject;
}
