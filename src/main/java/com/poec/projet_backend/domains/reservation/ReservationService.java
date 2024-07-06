package com.poec.projet_backend.domains.reservation;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public void delete(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
