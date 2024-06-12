package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.user_app.UserApp;

import java.util.List;

public record StudentDTO(
                Long id,
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
                                student.getMentors().stream().map(Mentor::getId).toList());
        }

        public static Student toStudent(StudentDTO student, UserApp userApp) {
                return Student.builder()
                        .id(student.id())
                        .description(student.description())
                        .firstName(student.firstname())
                        .lastName(student.lastname())
                        .title(student.title())
                        .imgUrl(student.imgUrl())
                        .githubUrl(student.githubUrl())
                        .linkedinUrl(student.linkedinUrl())
                        .user(userApp)
                        .build();
        }
}