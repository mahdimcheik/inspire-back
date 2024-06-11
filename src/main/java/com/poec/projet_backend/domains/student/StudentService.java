package com.poec.projet_backend.domains.student;


import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class StudentService {
    private final StudentRepository repository;
    private final UserAppRepository userAppRepository;

    public StudentDTO getStudentByUserId(Long userId){
        return StudentDTO.mapFromEntity(repository.findByUserId(userId));
    }

    public StudentDTO updateStudentByUserId(Long userId, Student student){
        Student studentToUpdate = repository.findByUserId(userId);
        studentToUpdate.setFirstName(student.getFirstName());
        studentToUpdate.setLastName(student.getLastName());
        studentToUpdate.setDescription(student.getDescription());
        studentToUpdate.setTitle(student.getTitle());
        studentToUpdate.setImgUrl(student.getImgUrl());
        studentToUpdate.setGithubUrl(student.getGithubUrl());
        studentToUpdate.setLinkedinUrl(student.getLinkedinUrl());

        return StudentDTO.mapFromEntity(repository.save(studentToUpdate));
    }

    public Student addStudentByUserId(StudentDTO student){
        UserApp user = userAppRepository.findById(student.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        return repository.save(StudentDTO.toStudent(student, user));
    }
}