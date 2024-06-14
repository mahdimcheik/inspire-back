package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public record MentorDTO (
        Long id,
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

    public static  MentorDTO fromEntity ( Mentor mentor) {
        return new MentorDTO(mentor.getId(),
                mentor.getFirstname(),
                mentor.getLastname(),
                mentor.getTitle(),
                mentor.getDescription(),
                mentor.getImgUrl(),
                mentor.getGithubUrl(),
                mentor.getLinkedinUrl(),
                mentor.getUser().getId());
    }

    public static Mentor fromDto (MentorDTO mentorDTO, UserApp user) {
        return Mentor.builder()
                .firstname(mentorDTO.firstname())
                .lastname(mentorDTO.lastname())
                .title(mentorDTO.title())
                .description(mentorDTO.description())
                .imgUrl(mentorDTO.imgUrl())
                .githubUrl(mentorDTO.githubUrl())
                .linkedinUrl(mentorDTO.linkedinUrl())
                .user(user)
                .build();
         
    }
}
