package com.poec.projet_backend.domains.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
