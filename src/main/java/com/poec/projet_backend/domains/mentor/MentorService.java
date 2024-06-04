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

    public Mentor updateMentorByUserId(Long userId, Mentor mentor){
        Mentor mentorToUpdate = repository.findByUserId(userId);
        mentorToUpdate.setFirstname(mentor.getFirstname());
        mentorToUpdate.setLastname(mentor.getLastname());
        mentorToUpdate.setTitle(mentor.getTitle());
        mentorToUpdate.setDescription(mentor.getDescription());
        mentorToUpdate.setImgUrl(mentor.getImgUrl());
        mentorToUpdate.setGithubUrl(mentor.getGithubUrl());
        mentorToUpdate.setLinkedinUrl(mentor.getLinkedinUrl());
        return repository.save(mentorToUpdate);
    }

}
