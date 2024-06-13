package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.*;
import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
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

//    public List<Map<String, Object>> getAllReservationInfosHistory(Long studentId, int perPage, int offset)
//    {
//        LocalDateTime time = LocalDateTime.now();
//        System.out.println("time now " +time);
//        return reservationRepository.findReservationInfosHistory(studentId, time,offset, perPage );
//    }

    public List<Map<String, Object>> getAllReservationByStudentIdInfosUpcoming(Long studentId, int perPage, int offset)
    {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("time now " +time);
        return reservationRepository.findReservationByStudentIdInfosUpComing(studentId, time,offset, perPage );
    }

    public List<Map<String, Object>> getAllReservationByStudentIdInfosHistory(Long studentId, int perPage, int offset)
    {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("time now " +time);
        return reservationRepository.findReservationByStudentInfosHistory(studentId, time,offset, perPage );
    }

    public Map<String, Object> getAllReservationByMentorIdInfosUpComing(Long mentorId, int perPage, int offset)
    {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("time now " +time);
        var results = reservationRepository.findReservationInfosByMentorIdUpComing(mentorId, time,offset, perPage );
        var res = results.stream().map(ele -> ResponseReservationForMentor.builder()
                .dateBegin(((Timestamp) ele.get("dateBegin")).toLocalDateTime())
                .dateEnd(((Timestamp) ele.get("dateEnd")).toLocalDateTime())
                .firstName((String) ele.get("firstName"))
                .lastName((String) ele.get("lastName"))
                .title((String) ele.get("title"))
                .imgUrl((String) ele.get("imgUrl"))
                .subject((String) ele.get("subject"))
                .message((String) ele.get("message"))
                .id((Long) ele.get("id"))
                .reservationId((Long) ele.get("reservationId"))
                .studentId((Long) ele.get("studentId"))
                .slotId((Long) ele.get("slotId"))
                .build()).toList();
        Map<String, Object> result = new HashMap<>();
        result.put("reservations",res);
        result.put("total",(Long) results.get(0).get("totalCount"));
        return result;
    }

    public List<Map<String, Object>> getAllReservationByMentorIdInfosHistory(Long mentorId, int perPage, int offset)
    {
        LocalDateTime time = LocalDateTime.now();
        System.out.println("time now " +time);
        return reservationRepository.findReservationInfosByMentorIdHistory(mentorId, time,offset, perPage );
    }

    public List<Map<String, Object>> delete(Long reservationId, Long studentId) {
        reservationRepository.deleteById(reservationId);
        return reservationRepository.findReservationInfos(studentId);
    }

}
