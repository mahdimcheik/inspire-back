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
}
