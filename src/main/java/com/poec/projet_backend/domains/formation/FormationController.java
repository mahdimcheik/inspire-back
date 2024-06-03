package com.poec.projet_backend.domains.formation;

import com.poec.projet_backend.domains.experience.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/formation")
public class FormationController {

    @Autowired
    private FormationService service;

    @GetMapping("/get/all")
    public ResponseEntity<List<Formation>> getAll() {
        List<Formation> coffees = service.getAll();
        return new ResponseEntity<>(coffees, HttpStatus.OK); // 200
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Formation> getById(@PathVariable Long id) {
        Formation formation = service.getById(id);
        return new ResponseEntity<>(formation, HttpStatus.OK); // 200
    }

    @PostMapping("/add")
    public ResponseEntity<Formation> add(@RequestBody Formation formation) {
        Formation newFormation = service.add(formation);
        return new ResponseEntity<>(newFormation, HttpStatus.CREATED); // 201
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Formation> update(@RequestBody Formation formation, @PathVariable Long id) {
        Formation updatedFormation = service.update(formation, id);
        return new ResponseEntity<>(updatedFormation, HttpStatus.OK); // 200

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
