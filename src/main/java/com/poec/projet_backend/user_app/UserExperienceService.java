package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.experience.ExperienceRepository;
import com.poec.projet_backend.domains.experience.ResponseExperience;
import com.poec.projet_backend.domains.formation.Formation;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
            newExperience.setTitle(experience.getTitle());
            newExperience.setCity(experience.getCity());
            newExperience.setCountry(experience.getCountry());
            newExperience.setCompany(experience.getCompany());
            newExperience.setDateEnd(experience.getDateEnd());
            newExperience.setDateBegin(experience.getDateBegin());
            experienceRepository.save(newExperience);
            return experienceRepository.findAllByUserId(experience.getUserId()).stream()
                    .map(ExperienceDTO::from).toList();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Transactional
    public ResponseExperience delete(Long id) {
        try {
            Experience experience = experienceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity with id " + id + " cannot be found"));
            experienceRepository.delete(experience);
            System.out.println("id " + id );
            List<ExperienceDTO> experiences = experienceRepository.findAllByUserId(experience.getUser().getId()).stream().map(ExperienceDTO::from).toList();
            System.out.println(experiences);
            return ResponseExperience.builder().experiences(experiences)
                    .message("Experience deleted")
                    .success(true)
                    .build();
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public long calculateTotalExperienceYears(Long userId) {
        List<Experience> experiences = experienceRepository.findAllByUserId(userId);
        return experiences.stream()
                .mapToLong(exp -> ChronoUnit.YEARS.between(exp.getDateBegin(), exp.getDateEnd() != null ? exp.getDateEnd() : LocalDate.now()))
                .sum();
    }
}
