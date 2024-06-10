package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.user_app.UserApp;

import java.util.List;

public record StudentDTO(
        long id,
        String firstname,
        String lastname,
        String title,
        String description,
        String imgUrl,
        String githubUrl,
        String linkedinUrl,
        Long userId,
        List<Long> mentorsIds

) {

        public static StudentDTO mapFromEntity(Student student) {
        return new StudentDTO(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getTitle(),
                student.getDescription(),
                student.getImgUrl(),
                student.getGithubUrl(),
                student.getLinkedinUrl(),
                student.getUser().getId(),
                student.getMentors().stream().map(Mentor::getId).toList()
        );
    }
}
