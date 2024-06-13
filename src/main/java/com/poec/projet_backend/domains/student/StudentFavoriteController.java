package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.experience.ResponseExperience;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Data
@RequestMapping("student/favorite")
public class StudentFavoriteController {

    private final StudentFavoriteService studentFavoriteService;

  @PostMapping("/add/{studentId}/{mentorId}")
    public List<Mentor> add(@PathVariable Long studentId, @PathVariable Long mentorId) {
        return studentFavoriteService.updateStudentFavoriteList(studentId, mentorId);
    }

    @DeleteMapping("/delete/{studentId}/{mentorId}")
    public ResponseEntity<?> delete(@PathVariable Long studentId, @PathVariable Long mentorId) {
        try {
            List<MentorDTO> mentors = studentFavoriteService.deleteStudentFavorite(studentId, mentorId);
            return ResponseEntity.ok(mentors);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
/*
    @GetMapping("/list/{studentId}")
    public List<Mentor> getFavorites(@PathVariable Long studentId) {
        return studentFavoriteService.getStudentFavorites(studentId);
    }

    @PostMapping("/add")
    public ResponseEntity<List<Mentor>> addFavorite(@RequestBody Map<String, Long> body) {
        Long studentId = body.get("studentId");
        Long mentorId = body.get("mentorId");
        try {
            System.out.println("Adding favorite - Student ID: " + studentId + ", Mentor ID: " + mentorId);
            List<Mentor> result = studentFavoriteService.updateStudentFavoriteList(studentId, mentorId);
            return ResponseEntity.status(201).body(result);
        } catch (Exception e) {
            System.out.println("Error adding favorite: " + e.getMessage());
            return ResponseEntity.status(401).body(null);
        }
    }

    @DeleteMapping("/delete/{studentId}/{mentorId}")
    public ResponseEntity<List<Mentor>> removeFavorite(@PathVariable Long studentId, @PathVariable Long mentorId) {
        try {
            List<Mentor> result = studentFavoriteService.deleteStudentFavorite(studentId, mentorId);
            return ResponseEntity.status(202).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

  */

    @GetMapping("/{studentId}/{mentorId}")
    public ResponseEntity<Map<String, Boolean>> isFavorite(@PathVariable Long studentId, @PathVariable Long mentorId) {
        try {
            boolean isFavorite = studentFavoriteService.isFavorite(studentId, mentorId);
            return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("isFavorite", false));
        }
    }

    @GetMapping("/list/{studentId}")
    public ResponseEntity<List<Mentor>> getUserFavorites(@PathVariable Long studentId) {
        try {
            List<Mentor> result = studentFavoriteService.getUserFavorites(studentId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

}
