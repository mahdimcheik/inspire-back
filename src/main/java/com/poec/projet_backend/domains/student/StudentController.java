package com.poec.projet_backend.domains.student;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;

    @GetMapping("/{userId}")
    public ResponseEntity<StudentDTO> getStudentByUserId(@PathVariable Long userId){
        try {
            return new ResponseEntity<>( service.getStudentByUserId(userId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<StudentDTO> updateStudentByUserId(@PathVariable Long userId, @RequestBody Student student){
        try{

        return new ResponseEntity<>( service.updateStudentByUserId(userId, student), HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long studentId){
        try {

        return new ResponseEntity<>( service.getStudentById(studentId), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>( null, HttpStatus.NOT_FOUND);
        }
    }
}
