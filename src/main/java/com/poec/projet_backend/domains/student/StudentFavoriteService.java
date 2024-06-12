package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.experience.ExperienceDTO;
import com.poec.projet_backend.domains.experience.ResponseExperience;
import com.poec.projet_backend.domains.formation.Formation;
import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class StudentFavoriteService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;

   /* public List<Mentor> getAll() {
        return mentorRepository.findAll();
    } */

    public List<MentorDTO> addFavorite(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        if (student.getMentors().contains(mentor)) {
            throw new RuntimeException("Mentor is already in favorites");
        }

        student.getMentors().add(mentor);
        studentRepository.save(student);

        return student.getMentors().stream().map(Mentor::toMentorDTO).toList();
    }


    public List<Mentor> updateStudentFavoriteList(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("User not found"));
        List<Mentor> mentors = student.getMentors();
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(()-> new RuntimeException("User not found"));
        mentors.add(mentor);
        student.setMentors(mentors);
        return studentRepository.save(student).getMentors();
    }

    /*
    public List<Mentor> deleteStudentFavorite(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        List<Mentor> mentors = student.getMentors();
        if (!mentors.remove(mentor)) {
            throw new RuntimeException("Mentor not in student's favorite list");
        }
        student.setMentors(mentors);

        return studentRepository.save(student).getMentors();
    }

    public List<Mentor> getStudentFavorites(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getMentors();
    } */

    public List<MentorDTO> deleteStudentFavorite(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Mentor mentor = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        if (!student.getMentors().contains(mentor)) {
            throw new RuntimeException("Mentor is not in favorites");
        }

        student.getMentors().remove(mentor);
        studentRepository.save(student);

        return student.getMentors().stream()
                .map(MentorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public boolean isFavorite(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getMentors().stream().anyMatch(mentor -> mentor.getId().equals(mentorId));
    }

    public List<Mentor> getUserFavorites(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return student.getMentors();
    }

}
