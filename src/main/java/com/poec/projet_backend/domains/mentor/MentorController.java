package com.poec.projet_backend.domains.mentor;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/mentor")

public class MentorController {
    private final MentorService service;

    @GetMapping("/{userId}")
    public Mentor getMentorByUserId(@PathVariable Long userId){
        return service.getMentorByUserId(userId);
    }

    @PutMapping("/{userId}")
    public Mentor updateMentorByUserId(@PathVariable Long userId, @RequestBody Mentor mentor){
        return service.updateMentorByUserId(userId, mentor);
    }
}
