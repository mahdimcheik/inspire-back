package com.poec.projet_backend.domains.experience;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExperienceDTO {
    private String title;
    private String company;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String city;
    private String country;
    private Long userId;

    static public ExperienceDTO from(Experience experience) {
        return ExperienceDTO.builder().city(experience.getCity())
                .company(experience.getCompany())
                .country(experience.getCountry())
                .title(experience.getTitle())
                .dateEnd(experience.getDateEnd())
                .dateBegin(experience.getDateBegin())
                .build();
    }
}
