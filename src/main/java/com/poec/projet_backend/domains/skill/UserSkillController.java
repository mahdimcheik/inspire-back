package com.poec.projet_backend.domains.skill;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/skill/user")
public class UserSkillController {
    private final UserSkillService userSkillService;

    @PutMapping("/update/{userId}")
    public ResponseSkill update(@PathVariable Long userId, @RequestBody List<Skill> skills) {
        try {
            return userSkillService.updateUserSkillList(userId, skills);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public List<SkillDTO> getSkillByUserId(@PathVariable Long userId) {
        try {
            return userSkillService.getSkillsByUserId(userId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
