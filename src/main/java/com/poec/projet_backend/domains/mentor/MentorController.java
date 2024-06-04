package com.poec.projet_backend.domains.mentor;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/mentor")

public class MentorController {
    private final MentorService service;

    @GetMapping("/{userId}")
    public Mentor getMentorByUserId(@PathVariable Long userId){
        return service.getMentorByUserId(userId);
    }
}
