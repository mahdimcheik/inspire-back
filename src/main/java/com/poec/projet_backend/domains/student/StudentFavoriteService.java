package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.formation.Formation;
import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class StudentFavoriteService {

    private final StudentRepository studentRepository;
    private final MentorRepository mentorRepository;

    public List<Mentor> getAll() {
        return mentorRepository.findAll();
    }

    public List<Mentor> updateStudentFavoriteList(Long studentId, Long mentorId) {
        Student student = studentRepository.findById(studentId).orElseThrow(()-> new RuntimeException("User not found"));
        List<Mentor> mentors = student.getMentors();
        Mentor mentor = mentorRepository.findById(mentorId).orElseThrow(()-> new RuntimeException("User not found"));
        mentors.add(mentor);
        student.setMentors(mentors);
        return studentRepository.save(student).getMentors();
    }

}
