package com.poec.projet_backend.domains.student;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    @GetMapping("/{userId}")
    public StudentDTO getStudentByUserId(@PathVariable Long userId){
        return service.getStudentByUserId(userId);
    }

    @PutMapping("/{userId}")
    public StudentDTO updateStudentByUserId(@PathVariable Long userId, @RequestBody Student student){
        return service.updateStudentByUserId(userId, student);
    }

    @GetMapping("/students/{studentId}")
    public StudentDTO getStudentById(@PathVariable Long studentId){
        return service.getStudentById(studentId);
    }

}
