package com.poec.projet_backend.domains.mentor;

import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data

public class MentorService {
    private final MentorRepository repository;

    public Mentor getMentorByUserId(Long userId){
        return repository.findByUserId(userId);
    }
}
