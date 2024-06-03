package com.poec.projet_backend.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@Entity
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String company;
    private LocalDate dateBegin;
    private LocalDate datEnd;
    private String city;
    private String country;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties("experience")
    @JoinColumn(name = "userId")
    private UserApp user;

}
