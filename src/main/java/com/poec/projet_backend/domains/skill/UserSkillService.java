package com.poec.projet_backend.domains.skill;

import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
public class UserSkillService {
    private final UserAppRepository userRepository;
    private final SkillRepository skillRepository;

    public List<SkillDTO> getSkillsByUserId(Long id) {
        UserApp user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getSkills().stream().map(SkillDTO::fromSkill).toList();
    }

    public ResponseSkill updateUserSkillList(Long id, List<Skill> skills) {
        UserApp user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        try {
            if (user != null) {
                user.setSkills(skills);
                var res = userRepository.save(user).getSkills().stream().map(SkillDTO::fromSkill).toList();
                return ResponseSkill.builder()
                        .message("success")
                        .success(true)
                        .skills(res)
                        .build();
            }
            return ResponseSkill.builder()
                    .message("failed")
                    .success(false)
                    .skills(new ArrayList<>())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}


