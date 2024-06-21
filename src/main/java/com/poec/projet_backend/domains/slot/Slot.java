package com.poec.projet_backend.domains.slot;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.reservation.Reservation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dateBegin")
    private LocalDateTime dateBegin;

    @Column(name = "dateEnd")
    private LocalDateTime dateEnd;

    @Column(name="visio" )
    private boolean visio;

    @Column(name = "booked")
    private boolean booked;

    @ManyToOne
    @JoinColumn(name = "mentorId")
    private Mentor mentor;

}
