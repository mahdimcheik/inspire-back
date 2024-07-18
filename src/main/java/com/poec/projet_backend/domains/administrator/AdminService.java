package com.poec.projet_backend.domains.administrator;

import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.domains.mentor.MentorRepository;
import com.poec.projet_backend.domains.student.StudentRepository;
import com.poec.projet_backend.user_app.UserAppService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Data
@Service
public class AdminService {
    private final UserAppService userAppService;
    private final MentorRepository mentorRepository;
    private final StudentRepository  studentRepository;

    public List<Map<String ,Object>> getAllMentors() {
        return mentorRepository.findAllMentorsDetailed();
    }

    public List<Map<String ,Object>> getAllStudents() {
        return studentRepository.findAllStudentsDetailed();
    }

    public List<Map<String ,Object>> getAllMentorsPaginatedASC(Long perPage, Long offset) {
        return mentorRepository.findAllMentorsDetailedPaginatedASC(perPage, offset);
    }
    public List<Map<String ,Object>> getAllStudentsPaginatedASC(Long perPage, Long offset) {
        return studentRepository.findAllStudentsDetailedPaginatedASC(perPage, offset);
    }

    public List<Map<String ,Object>> getAllMentorsPaginatedDESC(Long perPage, Long offset) {
        return mentorRepository.findAllMentorsDetailedPaginatedDESC(perPage, offset);
    }

    public List<Map<String ,Object>> getAllStudentsPaginatedDESC(Long perPage, Long offset) {
        return studentRepository.findAllStudentsDetailedPaginatedDESC(perPage, offset);
    }
}
