package com.poec.projet_backend.domains.reservation;

import com.poec.projet_backend.domains.slot.Slot;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.transaction.Transactional;
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
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin , s.dateEnd, r.studentId, st.title, st.imgUrl, st.firstname, st.lastname FROM reservation r, slot s, student st " +
                    "where r.studentId = ?1  and s.id = r.slotId and st.id = r.studentId",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationInfos(Long studentId);

    @Transactional
    @Query(
            value = "SELECT r.id as reservationId, r.subject, r.message ,  s.id as slotId, s.dateBegin, s.dateEnd,  s.visio, r.studentId, r.details, " +
                    "mt.title, mt.imgUrl, mt.firstname, mt.lastname, " +
                    "mt.userId as mentorUserId, " +
                    "st.userId as userId ," +
                    "COUNT(*) OVER() as totalCount " +
                    "FROM reservation r " +
                    "JOIN slot s ON s.id = r.slotId " +
                    "JOIN student st ON st.id = r.studentId " +
                    "JOIN mentor mt on mt.id = s.mentorId " +
                    "WHERE r.studentId = ?1 AND s.dateBegin >= ?2 " +
                    "ORDER BY s.dateBegin ASC " +
                    "LIMIT ?4 OFFSET ?3",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationByStudentIdInfosUpComing(Long studentId, LocalDateTime timeNow, int offset, int limit);

    @Transactional
    @Query(
            value = "SELECT r.*, r.id as reservationId, s.id as slotId, s.dateBegin, s.dateEnd, s.visio , " +
                    "mt.title, mt.imgUrl, mt.firstname, mt.lastname, " +
                    "mt.userId as mentorUserId, " +
                    "st.userId as userId ," +
                    "COUNT(*) OVER() as totalCount " +
                    "FROM reservation r " +
                    "JOIN slot s ON s.id = r.slotId " +
                    "JOIN student st ON st.id = r.studentId " +
                    "JOIN mentor mt on mt.id = s.mentorId " +
                    "WHERE r.studentId = ?1 AND s.dateBegin <= ?2 " +
                    "ORDER BY s.dateBegin DESC " +
                    "LIMIT ?4 OFFSET ?3",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationByStudentInfosHistory(Long studentId, LocalDateTime timeNow, int offset, int limit);

    @Transactional
    @Query(
            value = "SELECT r.id , r.subject, s.id as slotId, s.dateBegin, s.dateEnd, s.visio, r.studentId, r.details, " +
                    "st.title, st.imgUrl, st.firstname, st.lastname, " +
                    "mt.userId as mentorUserId, " +
                    "st.userId as userId ," +
                    "COUNT(*) OVER() as totalCount " +
                    "FROM reservation r " +
                    "JOIN slot s ON s.id = r.slotId " +
                    "JOIN student st ON st.id = r.studentId " +
                    "JOIN mentor mt on mt.id = s.mentorId " +
                    "WHERE s.mentorId = ?1 AND s.dateBegin >= ?2 " +
                    "ORDER BY s.dateBegin ASC " +
                    "LIMIT ?4 OFFSET ?3",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationInfosByMentorIdUpComing(Long mentorId, LocalDateTime timeNow, int offset, int limit);


    @Query(
            value = "SELECT r.id as reservationId, r.subject,r.message, s.id as slotId, s.dateBegin, s.dateEnd, s.visio, r.studentId, r.details , " +
                    "st.title, st.imgUrl, st.firstname, st.lastname, " +
                    "mt.userId as mentorUserId, " +
                    "st.userId as userId ," +
                    "COUNT(*) OVER() as totalCount " +
                    "FROM reservation r " +
                    "JOIN slot s ON s.id = r.slotId " +
                    "JOIN mentor mt on mt.id = s.mentorId " +
                    "JOIN student st ON st.id = r.studentId " +
                    "WHERE s.mentorId = ?1 AND s.dateBegin <= ?2 " +
                    "ORDER BY s.dateBegin DESC " +
                    "LIMIT ?4 OFFSET ?3",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationInfosByMentorIdHistory(Long mentorId, LocalDateTime timeNow, int offset, int limit);


    @Query(
            value = "DELETE FROM reservation "+
            "WHERE IN "+
            "(Select * from reservation rs JOIN slot sl on sl.id = rs.slotId WHERE sl.mentorId = :mentorId)",
            nativeQuery = true
    )
    void  deleteReservationsByMentorId(@Param("mentorId") Long mentorId);

}

