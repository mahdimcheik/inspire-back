package com.poec.projet_backend.user_app;


import com.poec.projet_backend.domains.formation.FormationDTO;
import com.poec.projet_backend.domains.formation.ResponseFormation;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/formation/user")
public class UserFormationController {
    private final UserFormationService userFormationService;
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
}
