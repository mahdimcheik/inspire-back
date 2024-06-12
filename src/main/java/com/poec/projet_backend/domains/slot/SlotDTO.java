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
    private boolean visio;
    private boolean isBooked;
    private Long mentorId;
    private Long reservationId;


    public static SlotDTO fromEntity(Slot slot) {
        return SlotDTO.builder()
                .id(slot.getId())

                .dateBegin(slot.getDateBegin())
                .dateEnd(slot.getDateEnd())
                .isBooked(slot.isBooked())
                .mentorId(slot.getMentor().getId())
                // .reservationId(slot.getReservation().getId())
                .visio(slot.isVisio())
                .build();
    }

    public static Slot toEntity(SlotDTO slotDTO, Mentor mentor, Reservation reservation) {
        return Slot.builder()
                .dateBegin(slotDTO.getDateBegin())
                .dateEnd(slotDTO.getDateEnd())
                .isBooked(false)
                .mentor(mentor)
                .visio(slotDTO.isVisio())
                //.reservation(reservation)
                .build();
    }
}
