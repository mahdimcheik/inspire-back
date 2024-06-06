package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.reservation.ReservationDTO;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@RequestMapping("/reservation/student")
public class StudentReservationController {

    private final StudentReservationService studentReservationService;

    @GetMapping("/{userId}")
    public List<ReservationDTO> getReservationByUserId(@PathVariable Long userId) {
        return studentReservationService.getReservationsByUserId(userId);
    }
}
