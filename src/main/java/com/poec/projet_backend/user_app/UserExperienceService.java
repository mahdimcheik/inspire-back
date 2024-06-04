package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.experience.ExperienceRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserExperienceService {

    private final UserAppRepository userRepository;
    private final ExperienceRepository experienceRepository;

    public List<ExperienceDTO> addUserExperience(ExperienceDTO experience) {
        try {

            UserApp userApp =  userRepository.findById(experience.getUserId()).orElseThrow(() ->  new RuntimeException() );
            Experience newExperience = new Experience();
            newExperience.setTitle(experience.getTitle());
            newExperience.setCompany(experience.getCompany());
            newExperience.setDateBegin(experience.getDateBegin());
            newExperience.setDateEnd(experience.getDateEnd());
            newExperience.setCity(experience.getCity());
            newExperience.setCountry(experience.getCountry());
            newExperience.setUser(userApp);


            experienceRepository.save(newExperience);
            return experienceRepository.findAllByUserId(experience.getUserId()).stream()
                    .map((exp)-> ExperienceDTO.builder().userId(newExperience.getUser().getId())
                            .title(exp.getTitle())
                            .company(exp.getCompany())
                            .dateBegin(exp.getDateBegin())
                            .dateEnd(exp.getDateEnd())
                            .city(exp.getCity())
                            .country(exp.getCountry())
                            .build()).toList();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ExperienceDTO> updateUserExperience(ExperienceDTO experience, Long id) {
        try {
            UserApp userApp =  userRepository.findById(experience.getUserId()).orElseThrow(() ->  new RuntimeException() );
            Experience newExperience = experienceRepository.findById(id).orElseThrow(() ->  new RuntimeException() );

            newExperience.setTitle(experience.getTitle());
            newExperience.setCompany(experience.getCompany());
            newExperience.setDateBegin(experience.getDateBegin());
            newExperience.setDateEnd(experience.getDateEnd());
            newExperience.setCity(experience.getCity());
            newExperience.setCountry(experience.getCountry());
            newExperience.setUser(userApp);

            experienceRepository.save(newExperience);
            return experienceRepository.findAllByUserId(experience.getUserId()).stream()
                    .map((experience1 -> ExperienceDTO.builder().city(experience1.getCity())
                            .country(experience1.getCountry())
                            .dateEnd(experience1.getDateEnd())
                            .title(experience1.getTitle())
                            .dateBegin(experience1.getDateBegin())
                            .company(experience1.getCompany())
                            .userId(userApp.getId())
                            .build()
                    )).toList();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
