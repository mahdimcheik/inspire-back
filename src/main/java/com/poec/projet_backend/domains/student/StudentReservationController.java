package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.ReservationDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/reservation")
public class StudentReservationController {

    private final StudentReservationService studentReservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationDTO>  add(@RequestBody final ReservationDTO reservationDTO) {
        return new ResponseEntity<>(studentReservationService.create(reservationDTO), HttpStatus.CREATED) ;
    }

//    @GetMapping("/get/{studentId}")
//    public ResponseEntity<List<ReservationDTO>> get(@PathVariable final Long studentId) {
//        var result = studentReservationService.getReservationsByStudentId(studentId);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/get/student/history/{studentId}/{perPage}/{offset}")
    public ResponseEntity<List<Map<String, Object>>> getStudentUpcomingReservation(@PathVariable final Long studentId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByStudentIdInfosHistory(studentId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/student/upcoming/{studentId}/{perPage}/{offset}")
    public ResponseEntity<List<Map<String, Object>>> getStudentHistoryReservation(@PathVariable final Long studentId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByStudentIdInfosUpcoming(studentId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/mentor/upcoming/{mentorId}/{perPage}/{offset}")
    public ResponseEntity<Map<String, Object>> getMentorUpComingReservation(@PathVariable final Long mentorId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByMentorIdInfosUpComing(mentorId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/get/mentor/history/{mentorId}/{perPage}/{offset}")
    public ResponseEntity<List<Map<String, Object>>> getMentorHistoryReservation(@PathVariable final Long mentorId,@PathVariable  int perPage,@PathVariable int offset) {
        var result = studentReservationService.getAllReservationByMentorIdInfosHistory(mentorId, perPage,  offset);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reservationId}/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> delete(@PathVariable final Long reservationId, @PathVariable final Long studentId) {
        return new ResponseEntity<>( studentReservationService.delete(reservationId, studentId), HttpStatus.OK);
    }

}
