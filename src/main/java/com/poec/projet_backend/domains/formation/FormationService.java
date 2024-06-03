package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.ExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormationService {

    @Autowired
    private FormationRepository repository;
}
