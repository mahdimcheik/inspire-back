package com.poec.projet_backend.domains.experience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/experience")
public class ExperienceController {

    @Autowired
    private ExperienceService service;
}
