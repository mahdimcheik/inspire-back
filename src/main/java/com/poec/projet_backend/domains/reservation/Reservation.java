package com.poec.projet_backend.domains.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studentId")
    @JsonIgnoreProperties("mentors")
    @JsonIgnore
    private Student student;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "slotId")
    private Slot slot;
}
