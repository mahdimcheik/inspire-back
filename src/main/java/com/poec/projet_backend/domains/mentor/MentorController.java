package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.domains.language.LanguageDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/mentor")

public class MentorController {
    private final MentorService service;

    @GetMapping("/get/all")
    public ResponseEntity<List<MentorDTO>> getAll() {
        List<Mentor> mentors = service.getAll();
        List<MentorDTO> mentorDTOS = mentors.stream().map(MentorDTO::fromEntity).toList();
        return new ResponseEntity<>(mentorDTOS, HttpStatus.OK); // 200
    }

    @GetMapping("/{userId}")
    public MentorDTO getMentorByUserId(@PathVariable Long userId){
        return service.getMentorByUserId(userId);
    }

    @GetMapping("/mentors/{mentorId}")
    public MentorDTO getMentorById(@PathVariable Long mentorId){
        return service.getMentorById(mentorId);
    }

    @PutMapping("/{userId}")
    public MentorDTO updateMentorByUserId(@PathVariable Long userId, @RequestBody MentorDTO mentor){
        return service.updateMentorByUserId(userId, mentor);
    }

    @PostMapping("/add")
    public MentorDTO addMentor(@RequestBody MentorDTO mentor){
        return service.addMentorByUserId(mentor).toMentorDTO();
    }

    @GetMapping("/by-skills")
    public ResponseEntity<List<MentorDTO>> getMentorsBySkills(@RequestParam List<String> skills) {
        List<MentorDTO> mentors = service.getMentorsBySkills(skills);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    @GetMapping("/by-experience")
    public ResponseEntity<List<MentorDTO>> getMentorsByExperienceYears(@RequestParam int minYears, @RequestParam int maxYears) {
        List<MentorDTO> mentors = service.getMentorsByExperienceYears(minYears, maxYears);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }


}
