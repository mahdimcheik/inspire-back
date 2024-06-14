package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.experience.ExperienceDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class FormationDTO {
    private Long id;
    private String title;
    private String company;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String city;
    private String country;
    private Long userId;

    static public FormationDTO from(Formation formation) {
        return FormationDTO.builder()
                .id(formation.getId())
                .title(formation.getTitle())
                .company(formation.getCompany())
                .dateBegin(formation.getDateBegin())
                .dateEnd(formation.getDateEnd())
                .city(formation.getCity())
                .country(formation.getCountry())
                .userId(formation.getUser().getId())
                .build();
    }
}
