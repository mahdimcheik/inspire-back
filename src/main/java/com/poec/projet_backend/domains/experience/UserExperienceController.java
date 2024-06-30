package com.poec.projet_backend.domains.experience;


import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Data
@RequestMapping("/experience/user")
public class UserExperienceController {
    private final UserExperienceService userExperienceService;
    private final ExperienceRepository experienceRepository;

    @PostMapping("/add")
    public ResponseExperience addUserExperience(@RequestBody ExperienceDTO experience) {
        try {
            return ResponseExperience.builder().experiences(userExperienceService.addUserExperience(experience))
                    .message("Expericence Added Succefuly")
                    .success(true)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseExperience.builder().experiences(null)
                    .message("Expericence not Added Succefuly")
                    .success(false)
                    .build();
        }
    }

    @PutMapping("/update/{experienceId}")
    public ResponseEntity<ResponseExperience> updateUserExperience(@RequestBody ExperienceDTO experience, @PathVariable Long experienceId) {
        try {
            return new ResponseEntity<>(ResponseExperience.builder().experiences(userExperienceService.updateUserExperience(experience, experienceId))
                    .message("Expericence updated Succefuly")
                    .success(true)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseExperience.builder().experiences(null)
                    .message(e.getMessage())
                    .success(false)
                    .build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{userId}")
    public List<ExperienceDTO> getUserExperience(@PathVariable Long userId) {
        return experienceRepository.findAllByUserId(userId).stream().map(experience -> ExperienceDTO.fromEntity(experience)).toList();

    }

    @DeleteMapping("/delete/{experienceId}")
    public ResponseExperience delete(@PathVariable Long experienceId) {
        return userExperienceService.delete(experienceId);
    }
}