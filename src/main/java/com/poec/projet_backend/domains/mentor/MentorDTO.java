package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public record MentorDTO (
    String firstname,
    String lastname,
    String title,
    String description,
    String imgUrl,
    String githubUrl,
    String linkedinUrl,
    Long userId
){
    public Mentor toMentor ( UserApp userApp) {
    return new Mentor().builder()
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
