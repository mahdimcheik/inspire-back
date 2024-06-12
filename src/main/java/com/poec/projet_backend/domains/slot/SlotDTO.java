package com.poec.projet_backend.domains.slot;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SlotDTO {
    private Long id;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private boolean visio;
    private boolean isBooked;
    private Long mentorId;
    private Long reservationId;
}
