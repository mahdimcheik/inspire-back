package com.poec.projet_backend.domains.reservation;

import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.student.Student;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private String subject;
    private String message;
    private Long studentId;
    private Long slotId;
    private String details;

    public static ReservationDTO toDTO(Reservation reservation) {
       return ReservationDTO.builder()
                .id(reservation.getId())
                .message(reservation.getMessage())
                .details(reservation.getDetails())
                .subject(reservation.getSubject())
                .slotId(reservation.getSlot().getId())
                .studentId(reservation.getStudent().getId())
                .build();
    }

    public static Reservation toEntity(ReservationDTO reservationDTO, Student student, Slot slot) {
        return Reservation.builder()
                .id(reservationDTO.getId())
                .message(reservationDTO.getMessage())
                .details(reservationDTO.getDetails())
                .subject(reservationDTO.getSubject())
                .student(student)
                .slot(slot)
                .build();
    }
}
