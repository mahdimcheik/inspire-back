package com.poec.projet_backend.domains.experience;

import com.poec.projet_backend.user_app.UserApp;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExperienceDTO {
    private Long id;
    private String title;
    private String company;
    private LocalDate dateBegin;
    private LocalDate dateEnd;
    private String city;
    private String country;
    private Long userId;

    static public ExperienceDTO fromEntity(Experience experience) {
        return ExperienceDTO.builder().city(experience.getCity())
                .company(experience.getCompany())
                .country(experience.getCountry())
                .title(experience.getTitle())
                .dateEnd(experience.getDateEnd())
                .dateBegin(experience.getDateBegin())
                .id(experience.getId())
                .userId(experience.getUser().getId())
                .build();
    }

    public static Experience fromDTO(ExperienceDTO experience, UserApp user) {
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
