package com.poec.projet_backend.domains.experience;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class ExperienceService {

    private final ExperienceRepository repository;

   public ExperienceDTO getExperienceById(Long id){
       Experience experience = repository.findById(id).orElse(null);

        return ExperienceDTO.fromEntity(experience);
   }
   public List<Experience> getAllExperienceByUserId(Long userId){
        return repository.findAllByUserId(userId);
   }
   public List<Experience> getAllExperiences(){
        return repository.findAll();
   }
   public void deleteExperience(Long id){
        repository.deleteById(id);
   }

   public Experience updateExperienceById(Long id, Experience experience){
        Experience experienceToUpdate = repository.findById(id).orElse(null);
        if (experienceToUpdate == null){
            return null;
        }
        experienceToUpdate.setTitle(experience.getTitle());
        experienceToUpdate.setCompany(experience.getCompany());
        experienceToUpdate.setDateBegin(experience.getDateBegin());
        experienceToUpdate.setDateEnd(experience.getDateEnd());
        experienceToUpdate.setCity(experience.getCity());
        experienceToUpdate.setCountry(experience.getCountry());
        return repository.save(experienceToUpdate);
   }
}
