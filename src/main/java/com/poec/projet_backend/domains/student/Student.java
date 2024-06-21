package com.poec.projet_backend.domains.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private String title;
    private String description;
    @Column(name = "imgUrl")
    private String imgUrl;
    @Column(name = "githubUrl")
    private String githubUrl;
    @Column(name = "linkedinUrl")
    private String linkedinUrl;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserApp user;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "favorite_mentor_student"
    )
    @JsonIgnoreProperties("students")
    @JsonIgnore
    private List<Mentor> mentors = new ArrayList<>();

    public StudentDTO toStudentDTO(){
        List<Long> mentorIds = new ArrayList<>();
        if(mentors != null){
            mentorIds = mentors.stream().map(Mentor::getId).toList();
        }
        return  new StudentDTO(this.getId(), this.getFirstname(), this.getLastname(), this.getTitle(), this.getDescription(), this.getImgUrl(), this.getGithubUrl(), this.getLinkedinUrl(), this.getUser().getId(), mentorIds);
    }
}