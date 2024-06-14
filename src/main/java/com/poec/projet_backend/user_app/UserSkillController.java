package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.skill.ResponseSkill;
import com.poec.projet_backend.domains.skill.Skill;
import com.poec.projet_backend.domains.skill.SkillDTO;
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
        return userSkillService.updateUserSkillList(userId, skills);
    }
    @GetMapping("/{userId}")
    public List<SkillDTO> getSkillByUserId(@PathVariable Long userId) {
        return userSkillService.getSkillsByUserId(userId);
    }
}
