package com.poec.projet_backend.domains.student;

import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.user_app.UserApp;

import java.util.List;

public record StudentDTO(
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

    public Student toStudent ( UserApp userApp) {
        return new Student().builder()
                .firstname(this.firstname())
                .lastname(this.lastname())
                .title(this.title())
                .description(this.description())
                .imgUrl(this.imgUrl())
                .githubUrl(this.githubUrl())
                .linkedinUrl(this.linkedinUrl())
                .user(userApp)
                .build();
    }
}