package com.poec.projet_backend.domains.reservation;

import lombok.Builder;
import lombok.Data;

public class ResponseReservation {
    private Long id;
    private Long studentId;

    public ResponseReservation (Long id, Long studentId) {
        this.id = id;
        this.studentId= studentId;
    }
}
