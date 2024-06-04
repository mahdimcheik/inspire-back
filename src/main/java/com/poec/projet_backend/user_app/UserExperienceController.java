package com.poec.projet_backend.user_app;

import com.poec.projet_backend.auth.AuthResponse;
import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.experience.ExperienceRepository;
import com.poec.projet_backend.domains.experience.ResponseExperience;
import lombok.Data;
import org.springframework.web.bind.annotation.*;


@RestController
@Data
@RequestMapping("/experience/user")
public class UserExperienceController {
    private final UserExperienceService userExperienceService;
    @PostMapping("/add")
    public ResponseExperience addUserExperience(@RequestBody ExperienceDTO experience) {
        try {
        return ResponseExperience.builder().experiences(userExperienceService.addUserExperience(experience))
                        .message("Expericence Added Succefuly")
                                .success(true)
                                        .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseExperience.builder().experiences(null)
                    .message("Expericence not Added Succefuly")
                    .success(false)
                    .build();
        }


    }

    @PutMapping("/update/{experienceId}")
    public ResponseExperience updateUserExperience(@RequestBody ExperienceDTO experience, @PathVariable Long experienceId) {
        try {
            return ResponseExperience.builder().experiences(userExperienceService.updateUserExperience(experience, experienceId))
                    .message("Expericence updated Succefuly")
                    .success(true)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseExperience.builder().experiences(null)
                    .message("Expericence not updated Succefuly")
                    .success(false)
                    .build();
        }


    }


}
