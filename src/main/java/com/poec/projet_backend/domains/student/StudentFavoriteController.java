package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.Mentor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("STUDENT/favorite")
public class StudentFavoriteController {

    private final StudentFavoriteService studentFavoriteService;

    @PutMapping("/add/{studentId}/{mentorId}")
    public List<Mentor> add(@PathVariable Long studentId, @PathVariable Long mentorId) {
        return studentFavoriteService.updateStudentFavoriteList(studentId, mentorId);
    }
}
