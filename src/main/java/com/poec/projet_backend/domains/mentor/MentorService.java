package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.domains.language.LanguageDTO;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Data
public class MentorService {
    private final MentorRepository repository;
    private final UserAppRepository userAppRepository;

    public List<Mentor> getAll() {
        return repository.findAll();
    }

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


    public Mentor addMentorByUserId(MentorDTO mentor){
        System.out.println("mentor in service " + mentor.toString());
        System.out.println("user Id " + mentor.userId());
        UserApp user = userAppRepository.findById(mentor.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("user in service " + user.toString());

        return repository.save(mentor.toMentor(user));
    }
}
