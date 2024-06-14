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
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin , s.dateEnd, r.studentId, st.title, st.imgUrl, st.firstName, st.lastName FROM reservation r, slot s, student st " +
                    "where r.studentId = ?1  and s.id = r.slotId and st.id = r.studentId",
            nativeQuery = true
    )
    List<Map<String, Object>> findReservationInfos(Long studentId);

    @Query(
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin, s.dateEnd, s.isBooked, s.isVisio, r.studentId, " +
                    "mt.title, mt.imgUrl, mt.firstName, mt.lastName, " +
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


    @Query(
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin, s.dateEnd,s.isBooked, s.isVisio, r.studentId, " +
                    "mt.title, mt.imgUrl, mt.firstName, mt.lastName, " +
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

    @Query(
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin, s.dateEnd, s.isBooked, s.isVisio, r.studentId, " +
                    "st.title, st.imgUrl, st.firstName, st.lastName, " +
                    "mt.userId as userId , " +
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
            value = "SELECT r.id as reservationId, r.subject, s.id as slotId, s.dateBegin, s.dateEnd, s.isBooked, s.isVisio, r.studentId, " +
                    "st.title, st.imgUrl, st.firstName, st.lastName, " +
                    "mt.userId as userId ," +
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


}

