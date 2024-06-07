package com.poec.projet_backend.domains.reservation;

import lombok.Builder;

@Builder
public record ReservationDTO(
        String subject,
        String message
) {
    static public ReservationDTO fromReservation(Reservation reservation) {
        return ReservationDTO.builder()
                .subject(reservation.getSubject())
                .message(reservation.getMessage())
                .build();
    }
}
