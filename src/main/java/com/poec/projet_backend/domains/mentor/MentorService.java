package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.domains.language.LanguageDTO;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import com.poec.projet_backend.user_app.UserExperienceService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class MentorService {
    private final MentorRepository repository;
    private final UserAppRepository userAppRepository;
    private final UserExperienceService userExperienceService;

    public List<Mentor> getAll() {
        return repository.findAll();
    }

    public MentorDTO getMentorByUserId(Long userId){
        Mentor mentor = repository.findByUserId(userId);

        return MentorDTO.fromEntity(mentor);
    }

    public MentorDTO updateMentorByUserId(Long userId, MentorDTO mentor){
        Mentor mentorToUpdate = repository.findByUserId(userId);
        mentorToUpdate.setFirstname(mentor.firstname());
        mentorToUpdate.setLastname(mentor.lastname());
        mentorToUpdate.setTitle(mentor.title());
        mentorToUpdate.setDescription(mentor.description());
        mentorToUpdate.setImgUrl(mentor.imgUrl());
        mentorToUpdate.setGithubUrl(mentor.githubUrl());
        mentorToUpdate.setLinkedinUrl(mentor.linkedinUrl());
        Mentor savedMentor = repository.save(mentorToUpdate);
        return MentorDTO.fromEntity(savedMentor);
    }


    public Mentor addMentorByUserId(MentorDTO mentor){
        System.out.println("mentor in service " + mentor.toString());
        System.out.println("user Id " + mentor.userId());
        UserApp user = userAppRepository.findById(mentor.userId()).orElseThrow(() -> new RuntimeException("User not found"));
       // System.out.println("user in service " + user.toString());
        return repository.save(mentor.toMentor(user));
    }


    public List<MentorDTO> getMentorsBySkills(List<String> skillNames) {
        List<Mentor> mentors = repository.findAll();

        Set<Long> userIdsWithAllSkills = mentors.stream()
                .filter(mentor -> hasAllSkills(mentor, skillNames))
                .map(mentor -> mentor.getUser().getId())
                .collect(Collectors.toSet());

        List<Mentor> filteredMentors = mentors.stream()
                .filter(mentor -> userIdsWithAllSkills.contains(mentor.getUser().getId()))
                .collect(Collectors.toList());

        return filteredMentors.stream()
                .map(MentorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    private boolean hasAllSkills(Mentor mentor, List<String> skillNames) {
        List<String> mentorSkills = mentor.getUser().getSkills().stream()
                .map(skill -> skill.getName())
                .collect(Collectors.toList());
        return mentorSkills.containsAll(skillNames);
    }

    public List<MentorDTO> getMentorsByExperienceYears(int minYears, int maxYears) {
        List<Mentor> mentors = repository.findAll();
        return mentors.stream()
                .filter(mentor -> {
                    long totalExperienceYears = userExperienceService.calculateTotalExperienceYears(mentor.getUser().getId());
                    return totalExperienceYears >= minYears && totalExperienceYears <= maxYears;
                })
                .map(MentorDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
