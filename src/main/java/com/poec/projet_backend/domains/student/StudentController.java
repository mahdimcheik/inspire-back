package com.poec.projet_backend.domains.student;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/STUDENT")
public class StudentController {
    private final StudentService service;

    @GetMapping("/{userId}")
    public Student getStudentByUserId(@PathVariable Long userId){
        return service.getStudentByUserId(userId);
    }

    @PutMapping("/{userId}")
    public Student updateStudentByUserId(@PathVariable Long userId, @RequestBody Student student){
        return service.updateStudentByUserId(userId, student);
    }
}
