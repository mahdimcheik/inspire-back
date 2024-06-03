package com.poec.projet_backend.domains.experience;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class ExperienceService {

    private ExperienceRepository repository;

    public Experience createExperience(Experience experience){
        return repository.save(experience);

    }
}
