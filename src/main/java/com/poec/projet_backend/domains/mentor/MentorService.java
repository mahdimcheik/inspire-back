package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.domains.slot.SlotRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
@AllArgsConstructor
public class MentorService {
    private final MentorRepository repository;
    private final UserAppRepository userAppRepository;
    // private final UserSlotService userSlotService;
    private final SlotRepository slotRepository;

    public List<Mentor> getAll() {
        return repository.findAll();
    }

    public MentorDTO getMentorByUserId(Long userId){
        Mentor mentor = repository.findByUserId(userId);

        return MentorDTO.fromEntity(mentor);
    }

    public MentorDTO getMentorById(Long mentorId){
        Mentor mentor = repository.findById(mentorId).get();

        return MentorDTO.fromEntity(mentor);
    }

    public MentorDTO updateMentorByUserId(Long userId, MentorDTO mentor){
        Mentor mentorToUpdate = repository.findByUserId(userId);
        mentorToUpdate.setFirstname(mentor.getFirstname());
        mentorToUpdate.setLastname(mentor.getLastname());
        mentorToUpdate.setTitle(mentor.getTitle());
        mentorToUpdate.setDescription(mentor.getDescription());
        mentorToUpdate.setImgUrl(mentor.getImgUrl());
        mentorToUpdate.setGithubUrl(mentor.getGithubUrl());
        mentorToUpdate.setLinkedinUrl(mentor.getLinkedinUrl());
        Mentor savedMentor = repository.save(mentorToUpdate);
        return MentorDTO.fromEntity(savedMentor);
    }


    public Mentor addMentorByUserId(MentorDTO mentor){
        UserApp user = userAppRepository.findById(mentor.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        return repository.save(MentorDTO.fromDTO(mentor, user));
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



    public List<MentorDTO> getMentorsByAvailability(String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start;
        LocalDateTime end;

        switch (period.toLowerCase()) {
            case "day":
                start = now.withHour(0).withMinute(0).withSecond(0).withNano(0);
                end = start.plusDays(1).minusSeconds(1);
                break;
            case "week":
                start = now.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                end = start.plusWeeks(1).with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY)).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
                break;
            case "any":
                start = LocalDateTime.MIN;
                end = LocalDateTime.MAX;
                break;
            default:
                throw new IllegalArgumentException("Invalid period specified: " + period);
        }

        return repository.findAll().stream()
                .filter(mentor -> {
                    if (period.equalsIgnoreCase("any")) {
                        return mentor.getSlots().stream().anyMatch(slot -> !slot.isBooked());
                    } else {
                        return slotRepository.findAvailableSlotsByMentorIdAndDateRange(mentor.getId(), start, end).size() > 0;
                    }
                })
                .map(MentorDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
