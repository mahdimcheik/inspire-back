package com.poec.projet_backend.domains.student;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    @GetMapping("/{userId}")
    public Student getStudentByUserId(@PathVariable Long userId){
        return service.getStudentByUserId(userId);
    }
}
