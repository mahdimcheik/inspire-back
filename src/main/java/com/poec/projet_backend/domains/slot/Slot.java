package com.poec.projet_backend.domains.slot;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isBooked")
    private boolean isBooked;

}
