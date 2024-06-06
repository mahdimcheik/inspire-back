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
            Experience newExperience = Experience.from(experience,userApp);
            experienceRepository.save(newExperience);
            return experienceRepository.findAllByUserId(experience.getUserId()).stream()
                    .map(ExperienceDTO::from).toList();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<ExperienceDTO> updateUserExperience(ExperienceDTO experience, Long id) {
        try {
            UserApp userApp =  userRepository.findById(experience.getUserId()).orElseThrow(() ->  new RuntimeException() );
            Experience newExperience = experienceRepository.findById(id).orElseThrow(() ->  new RuntimeException() );
            experienceRepository.save(newExperience);
            return experienceRepository.findAllByUserId(experience.getUserId()).stream()
                    .map(ExperienceDTO::from).toList();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
