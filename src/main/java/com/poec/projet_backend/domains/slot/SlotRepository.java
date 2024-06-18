package com.poec.projet_backend.domains.slot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public interface SlotRepository extends JpaRepository<Slot, Long> {
    List<Slot> findAllByMentorId(Long id);

    @Query(
            value = "SELECT * FROM slot u WHERE u.dateEnd BETWEEN :start AND :end",
            nativeQuery = true
    )
    List<Slot> findAllActiveSlotNative(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);



    @Query(
            value = "SELECT * FROM slot u WHERE u.dateEnd BETWEEN :start AND :end AND u.mentorId = :mentorId",
            nativeQuery = true
    )
    List<Slot> findAllActiveUsersSlotsNative(@Param("mentorId") Long mentorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(
            value = "SELECT s.*, r.id as reservationId FROM slot s " +
                    "LEFT JOIN reservation r ON r.slotId = s.id " +
                    "WHERE s.dateEnd >= :start AND s.dateEnd <= :end AND s.mentorId = :mentorId AND (s.booked = false OR r.studentId = :studentId)",
            nativeQuery = true
    )
    List<Map<String, Object>> getSlotsforStudentByMentorId(@Param("mentorId") Long mentorId,@Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
//@Param("studentId") Long studentId,
