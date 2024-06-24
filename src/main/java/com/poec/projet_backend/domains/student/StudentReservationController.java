package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.reservation.ReservationDTO;
import com.poec.projet_backend.domains.reservation.ReservationRepository;
import jakarta.persistence.Convert;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/reservation")
public class StudentReservationController {

    private final StudentReservationService studentReservationService;
    private final ReservationRepository reservationRepository;

    @PostMapping("/add")
    public ResponseEntity<ReservationDTO>  add(@RequestBody final ReservationDTO reservationDTO) throws Exception {
        return new ResponseEntity<>(studentReservationService.create(reservationDTO), HttpStatus.CREATED) ;
    }

    @GetMapping("/get/student/history/{studentId}/{perPage}/{offset}")
    public ResponseEntity<Map<String, Object
            >> getStudentUpcomingReservation(@PathVariable final Long studentId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByStudentIdInfosHistory(studentId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/student/upcoming/{studentId}/{perPage}/{offset}")
    public ResponseEntity<Map<String, Object>> getStudentHistoryReservation(@PathVariable final Long studentId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByStudentIdInfosUpcoming(studentId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/mentor/upcoming/{mentorId}/{perPage}/{offset}")
    public ResponseEntity<Map<String, Object>> getMentorUpComingReservation(@PathVariable final Long mentorId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByMentorIdInfosUpComing(mentorId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/mentor/history/{mentorId}/{perPage}/{offset}")
    public ResponseEntity<Map<String, Object>> getMentorHistoryReservation(@PathVariable final Long mentorId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByMentorIdInfosHistory(mentorId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/student/{reservationId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable final Long reservationId) {
       return new ResponseEntity<>( studentReservationService.delete(reservationId), HttpStatus.OK);
    }

    @DeleteMapping("/delete/mentor/{reservationId}")
    public ResponseEntity<Map<String, Object>> deleteByMentor(@PathVariable final Long reservationId) {
        return new ResponseEntity<>( studentReservationService.deleteReservationAndSlot(reservationId), HttpStatus.OK);
    }

    @PutMapping("/update/{reservationId}/{first}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable final Long reservationId,@PathVariable int first,  @RequestBody final Map<String, String> message) {
        try {
            var reservation = studentReservationService.update(reservationId,first, message.get("message"));
            Long mentorId = Long.valueOf( message.get("mentorId"));
            System.out.println("mentorId: " + mentorId);
            System.out.println("first" + first);
            var reservations = studentReservationService.getAllReservationByMentorIdInfosHistory(mentorId, 5, first);
            return new ResponseEntity<>(reservations, HttpStatus.OK);
        }catch (Exception ex)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
