package com.poec.projet_backend.domains.reservation;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String message;
}
