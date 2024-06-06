package com.poec.projet_backend.domains.skill;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Data
@RestController
@RequestMapping("/skill")
public class SkillController {

    private final SkillService service;

    @GetMapping("/get/all")
    public List<SkillDTO> getAllSkills() {
        return service.getAllSkills();
    }
}
