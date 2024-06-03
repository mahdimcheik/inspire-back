package com.poec.projet_backend.slot;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dateBegin")
    private LocalDate dateBegin;
    @Column(name = "dateEnd")
    private LocalDate dateEnd;
    private boolean visio;
    @Column(name = "userId")
    private int userId;

}
