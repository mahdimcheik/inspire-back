package com.poec.projet_backend.domains.formation;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
        try {
            return repository.save(formation);
        }catch (Exception e) {
            throw new EntityExistsException("Entity with id " + formation.getId() + " already exists");
        }
    }

    public void delete(Long id) {
        if(repository.findById(id).isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " cannot be found");
        }
    }

    public Formation update(Formation formation, Long id) {
        try {
            Formation existingFormation = getById(id);

            existingFormation.setTitle(formation.getTitle());
            existingFormation.setCompany(formation.getCompany());
            existingFormation.setDateBegin(formation.getDateBegin());
            existingFormation.setDateEnd(formation.getDateEnd());
            existingFormation.setCity(formation.getCity());
            existingFormation.setCountry(formation.getCountry());
            return repository.save(existingFormation);
        }
  catch (Exception e) {
            throw new RuntimeException("Entity with id " + id + " already exists");
  }

    }

}
