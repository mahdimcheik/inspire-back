package com.poec.projet_backend.domains.experience;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService service;

//    @PostMapping("/add")
//
//    public Experience addExperience (@RequestBody Experience experience){
//        return service.createExperience(experience);
//    }

    @GetMapping("/{id}")
    public Experience getExperienceById(@PathVariable Long id){
        return service.getExperienceById(id);
    }
    @GetMapping("/user/{userId}")
    public List<Experience> getExperienceByUserId(@PathVariable Long userId){
        return service.getAllExperienceByUserId(userId);
    }
    @GetMapping("/all")
    public List<Experience> getAllExperiences(){
        return service.getAllExperiences();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteExperience(@PathVariable Long id){
        service.deleteExperience(id);
    }
    @PutMapping("/update/{id}")
    public Experience updateExperience(@RequestBody Experience experience, @PathVariable Long id){
        return service.updateExperienceById(id,experience);
    }
}
