package com.poec.projet_backend.domains.formation;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/formation/user")
public  class UserFormationController {
    private final ResponseFormation.UserFormationService userFormationService;
    private final FormationRepository formationRepository;

    @PostMapping("/add")
    public ResponseEntity< ResponseFormation> addUserFormation(@RequestBody FormationDTO formation) {
        try {
            return new ResponseEntity<>( ResponseFormation.builder().formations(userFormationService.addUserFormation(formation))
                    .message("Formation Added Succefuly")
                    .success(true)
                    .build(), HttpStatus.CREATED);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new  ResponseEntity (ResponseFormation.builder().formations(null)
                    .message("Formation not Added Succefuly")
                    .success(false)
                    .build(), HttpStatus.BAD_REQUEST);
        }


    }

    @PutMapping("/update/{formationId}")
    public ResponseEntity< ResponseFormation> updateUserFormation(@RequestBody FormationDTO formation, @PathVariable Long formationId) {
        try {
            System.out.println("formation id " + formationId);
            System.out.println("formation " + formation);
            return new ResponseEntity<>( ResponseFormation.builder().formations(userFormationService.updateUserFormation(formation, formationId))
                    .message("Formation updated Succefuly")
                    .success(true)
                    .build(), HttpStatus.OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( ResponseFormation.builder().formations(null)
                    .message("Formation not updated Succefuly")
                    .success(false)
                    .build(), HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/{userId}")
    public ResponseEntity< List<FormationDTO>> getUserFormation(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>( formationRepository.findAllByUserId(userId).stream().map(formation -> FormationDTO.fromEntity(formation)).toList(), HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>( null, HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete/{formationId}")
    public ResponseEntity< ResponseFormation> delete(@PathVariable Long formationId) {

        try {
            return new ResponseEntity<>( userFormationService.delete(formationId), HttpStatus.OK);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}