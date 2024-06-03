package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/formation")
public class FormationController {

    @Autowired
    private FormationService service;
}
