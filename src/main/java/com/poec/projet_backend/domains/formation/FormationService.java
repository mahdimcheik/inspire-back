package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.ExperienceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormationService {

    @Autowired
    private FormationRepository repository;

    public List<Formation> getAll() {
        return repository.findAll();
    }

    public Formation getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new EntityNotFoundException("Entity with id " + id + " cannot be found"));
    }

    public Formation add(Formation formation) {
        return repository.save(formation);
    }

    public void delete(Long id) {
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " cannot be found");
        }
    }

    public Formation update(Formation formation, Long id) {
        Formation existingFormation = getById(id);

        existingFormation.setTitle(formation.getTitle());
        existingFormation.setCompany(formation.getCompany());
        existingFormation.setDateBegin(formation.getDateBegin());
        existingFormation.setDateEnd(formation.getDateEnd());
        existingFormation.setCity(formation.getCity());
        existingFormation.setCountry(formation.getCountry());
        return repository.save(existingFormation);

    }
}
