package com.poec.projet_backend.domains.mentor;

import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MentorDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String title;
    private String description;
    private String imgUrl;
    private String githubUrl;
    private String linkedinUrl;
    private Long userId;

    public static MentorDTO fromEntity(Mentor mentor) {
        return MentorDTO.builder()
                .id(mentor.getId())
                .firstname(mentor.getFirstname())
                .lastname(mentor.getLastname())
                .title(mentor.getTitle())
                .description(mentor.getDescription())
                .imgUrl(mentor.getImgUrl())
                .githubUrl(mentor.getGithubUrl())
                .linkedinUrl(mentor.getLinkedinUrl())
                .userId(mentor.getUser().getId())
                .build();
    }

    public static Mentor fromDTO(MentorDTO mentorDTO, UserApp user) {
        return Mentor.builder()
                .firstname(mentorDTO.getFirstname())
                .lastname(mentorDTO.getLastname())
                .title(mentorDTO.getTitle())
                .description(mentorDTO.getDescription())
                .imgUrl(mentorDTO.getImgUrl())
                .githubUrl(mentorDTO.getGithubUrl())
                .linkedinUrl(mentorDTO.getLinkedinUrl())
                .user(user)
                .build();
    }
}
