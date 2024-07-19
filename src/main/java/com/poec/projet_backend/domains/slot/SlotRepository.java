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
            value = "SELECT s.*, st.firstname, st.lastname, st.imgUrl, r.id as reservationId, r.subject  , s.dateBegin, s.dateEnd FROM slot s " +
                    "LEFT JOIN reservation r ON r.slotId = s.id " +
                    "LEFT JOIN student st ON r.studentId = st.id",
            nativeQuery = true
    )
    List<Map<String, String>> findAllByMentorIdDetailed(Long id);


    @Query(
            value = "SELECT s.*, r.subject, r.id as reservationId, r.details, r.message, st.firstname, st.lastname, st.imgUrl  " +
                    "FROM slot s " +
                    "LEFT JOIN  reservation r on s.id = r.slotId " +
                    "LEFT JOIN student st on r.studentId = st.id " +
                    "WHERE s.dateEnd BETWEEN :start AND :end AND s.mentorId = :mentorId",
            nativeQuery = true
    )
    List<Map<String, Object>> findAllActiveUsersSlotsNative(@Param("mentorId") Long mentorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


    @Query(
            value = "SELECT s.*, r.id as reservationId FROM slot s " +
                    "LEFT JOIN reservation r ON r.slotId = s.id " +
                    "WHERE s.dateBegin >= :start AND s.dateBegin<= :end AND s.mentorId = :mentorId AND (r.id IS NULL or r.studentId = :studentId)",
            nativeQuery = true
    )
    List<Map<String, Object>> getSlotsByMentorIdForStudent(@Param("mentorId") Long mentorId, @Param("studentId") Long studentId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT s FROM Slot s WHERE s.mentor.id = :mentorId  AND s.dateEnd BETWEEN :start AND :end")
    List<Slot> findAvailableSlotsByMentorIdAndDateRange(@Param("mentorId") Long mentorId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    void deleteByMentorId(Long mentorId);
}


