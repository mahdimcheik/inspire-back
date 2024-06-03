package com.poec.projet_backend.domains.experience;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/experience")
public class ExperienceController {

    private ExperienceService service;

    @PostMapping("/add")

    public Experience addExperience (@RequestBody Experience experience){
        return service.createExperience(experience);

    }
}
