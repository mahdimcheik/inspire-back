package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.*;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
public class StudentReservationService {

    private final StudentRepository studentRepository;
    private final ReservationRepository reservationRepository;
    private final SlotRepository slotRepository;

    public ReservationDTO create(ReservationDTO reservationDTO) {
        if(reservationDTO.getStudentId() != null) {
            var student = studentRepository.findById(reservationDTO.getStudentId());
            var slot = slotRepository.findById(reservationDTO.getSlotId());
            if(student.isPresent() && slot.isPresent()) {
                Reservation reservation = Reservation.builder()
                        .slot(slot.get())
                        .subject(reservationDTO.getSubject())
                        .message("")
                        .student(student.get())
                        .build();
                return ReservationDTO.toDTO(reservationRepository.save(reservation));
            }
        }
        return null;
    }

    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(ReservationDTO::toDTO).toList();
    }
    public List<ReservationDTO> getReservationsByStudentId(Long studentId) {
        return reservationRepository.findReservationsByStudentId(studentId).stream().map(ReservationDTO::toDTO).toList();
    }

    public List<Map<String, Object>> test(Long studentId)
    {
        return reservationRepository.findReservationInfos(studentId);
    }
}
