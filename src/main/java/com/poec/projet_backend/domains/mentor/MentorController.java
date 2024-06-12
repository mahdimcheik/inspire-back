package com.poec.projet_backend.domains.mentor;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/mentor")

public class MentorController {
    private final MentorService service;

    @GetMapping("/{userId}")
    public MentorDTO getMentorByUserId(@PathVariable Long userId){
        return service.getMentorByUserId(userId);
    }

    @PutMapping("/{userId}")
    public MentorDTO updateMentorByUserId(@PathVariable Long userId, @RequestBody MentorDTO mentor){
        return service.updateMentorByUserId(userId, mentor);
    }

    @PostMapping("/add")
    public MentorDTO addMentor(@RequestBody MentorDTO mentor){
        return service.addMentorByUserId(mentor).toMentorDTO();
    }
}
