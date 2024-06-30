package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.mentor.MentorDTO;
import com.poec.projet_backend.user_app.UserApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO{
        private  Long id;
        private  String firstname;
        private  String lastname;
        private  String title;
        private  String description;
        private  String imgUrl;
        private  String githubUrl;
        private  String linkedinUrl;
        private  Long userId;
        private List<Long> mentorIds;



        public static StudentDTO fromEntity(Student student) {
                var ids = student.getMentors().stream().map(Mentor::getId).toList();
                return StudentDTO.builder()
                        .id(student.getId())
                        .firstname(student.getFirstname())
                        .lastname(student.getLastname())
                        .title(student.getTitle())
                        .description(student.getDescription())
                        .imgUrl(student.getImgUrl())
                        .githubUrl(student.getGithubUrl())
                        .linkedinUrl(student.getLinkedinUrl())
                        .userId(student.getId())
                        .mentorIds(ids)
                        .build();
        }

        public static Student fromDTO(StudentDTO student, UserApp userApp) {
                return Student.builder()
                        .id(student.getId())
                        .description(student.getDescription())
                        .firstname(student.getFirstname())
                        .lastname(student.getLastname())
                        .title(student.getTitle())
                        .imgUrl(student.getImgUrl())
                        .githubUrl(student.getGithubUrl())
                        .linkedinUrl(student.getLinkedinUrl())
                        .user(userApp)
                        .build();
        }
}