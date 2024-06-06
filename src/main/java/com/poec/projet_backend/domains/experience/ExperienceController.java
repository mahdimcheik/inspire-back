package com.poec.projet_backend.domains.experience;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService service;

    @GetMapping("/{id}")
    public ExperienceDTO  getExperienceById(@PathVariable Long id){
        return service.getExperienceById(id);
    }
}
