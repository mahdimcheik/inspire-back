package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.experience.ExperienceRepository;
import com.poec.projet_backend.domains.experience.ResponseExperience;
import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.formation.FormationRepository;
import com.poec.projet_backend.domains.formation.ResponseFormation;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/formation/user")
public class UserFormationController {
    private final UserFormationService userFormationService;
    private final FormationRepository formationRepository;

    @PostMapping("/add")
    public ResponseFormation addUserFormation(@RequestBody FormationDTO formation) {
        try {
            return ResponseFormation.builder().formations(userFormationService.addUserFormation(formation))
                    .message("Formation Added Succefuly")
                    .success(true)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseFormation.builder().formations(null)
                    .message("Formation not Added Succefuly")
                    .success(false)
                    .build();
        }


    }

    @PutMapping("/update/{formationId}")
    public ResponseFormation updateUserFormation(@RequestBody FormationDTO formation, @PathVariable Long formationId) {
        try {
            return ResponseFormation.builder().formations(userFormationService.updateUserFormation(formation, formationId))
                    .message("Formation updated Succefuly")
                    .success(true)
                    .build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseFormation.builder().formations(null)
                    .message("Formation not updated Succefuly")
                    .success(false)
                    .build();
        }


    }

    @GetMapping("/{userId}")
    public List<FormationDTO> getUserFormation(@PathVariable Long userId) {
        return formationRepository.findAllByUserId(userId).stream().map(formation -> FormationDTO.from(formation)).toList();

    }


    @DeleteMapping("/delete/{formationId}")
    public ResponseEntity<Void> delete(@PathVariable Long formationId) {
        userFormationService.delete(formationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
