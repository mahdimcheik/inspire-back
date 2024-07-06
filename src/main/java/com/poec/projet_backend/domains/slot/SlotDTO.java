package com.poec.projet_backend.domains.slot;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.reservation.Reservation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SlotDTO {
    private Long id;
    private LocalDateTime dateBegin;
    private LocalDateTime dateEnd;
    private boolean isVisio;
    private Long mentorId;
    private Long reservationId;


    public static SlotDTO fromEntity(Slot slot) {
        return SlotDTO.builder()
                .id(slot.getId())
                .dateBegin(slot.getDateBegin())
                .dateEnd(slot.getDateEnd())
                .mentorId(slot.getMentor().getId())
                // .reservationId(slot.getReservation().getId())
                .isVisio(slot.isVisio())
                .build();
    }

    public static Slot toEntity(SlotDTO slotDTO, Mentor mentor, Reservation reservation) {
        return Slot.builder()
                .dateBegin(slotDTO.getDateBegin())
                .dateEnd(slotDTO.getDateEnd())
                .mentor(mentor)
                .visio(slotDTO.isVisio())
                //.reservation(reservation)
                .build();
    }
}
