package com.poec.projet_backend.domains.student;


import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class StudentService {
    private final StudentRepository repository;

    public Student getStudentByUserId(Long userId){
        return repository.findByUserId(userId);
    }

    public Student updateStudentByUserId(Long userId, Student student){
        Student studentToUpdate = repository.findByUserId(userId);
        studentToUpdate.setFirstname(student.getFirstname());
        studentToUpdate.setLastname(student.getLastname());
        studentToUpdate.setDescription(student.getDescription());
        studentToUpdate.setTitle(student.getTitle());
        studentToUpdate.setImgUrl(student.getImgUrl());
        studentToUpdate.setGithubUrl(student.getGithubUrl());
        studentToUpdate.setLinkedinUrl(student.getLinkedinUrl());
        return repository.save(studentToUpdate);
    }
}
