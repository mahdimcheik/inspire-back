package com.poec.projet_backend.domains.skill;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public Skill addSkill(@RequestBody Skill skill) {
        return service.addSkill(skill);
    }
}
