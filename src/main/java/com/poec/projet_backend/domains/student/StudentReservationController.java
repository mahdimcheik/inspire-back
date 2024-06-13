package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.domains.reservation.ReservationDTO;
import com.poec.projet_backend.domains.reservation.ResponseReservation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("/reservation/student")
public class StudentReservationController {

    private final StudentReservationService studentReservationService;

    @PostMapping("/add")
    public ResponseEntity<ReservationDTO>  add(@RequestBody final ReservationDTO reservationDTO) {
        return new ResponseEntity<>(studentReservationService.create(reservationDTO), HttpStatus.CREATED) ;
    }

    @GetMapping("/get/{studentId}")
    public ResponseEntity<List<ReservationDTO>> get(@PathVariable final Long studentId) {
        var result = studentReservationService.getReservationsByStudentId(studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/test/{studentId}")
    public ResponseEntity<List<Map<String, Object>>> test(@PathVariable final Long studentId) {
        var result = studentReservationService.test(studentId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
