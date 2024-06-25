package com.poec.projet_backend.domains.experience;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String company;
    @Column(name = "dateBegin")
    private LocalDate dateBegin;
    @Column(name = "dateEnd")
    private LocalDate dateEnd;
    private String city;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserApp user;

    public static Experience from(ExperienceDTO experience, UserApp user) {
        return Experience.builder()
                .city(experience.getCity())
                .country(experience.getCountry())
                .company(experience.getCompany())
                .dateBegin(experience.getDateBegin())
                .dateEnd(experience.getDateEnd())
                .title(experience.getTitle())
                .user(user)
                .build();
    }

}
