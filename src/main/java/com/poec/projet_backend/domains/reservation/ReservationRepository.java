package com.poec.projet_backend.domains.reservation;

import com.poec.projet_backend.domains.slot.Slot;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findReservationsByStudentId(Long studentId);

    @Query(
            value = "SELECT r.subject, s.dateBegin , s.dateEnd, r.studentId FROM reservation r, slot s " +
                    "where r.studentId = ?1  and s.id = r.slotId",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationInfos(Long studentId);
}
