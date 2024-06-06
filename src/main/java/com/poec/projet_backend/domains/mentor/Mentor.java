package com.poec.projet_backend.domains.mentor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.domains.slot.Slot;
import com.poec.projet_backend.domains.student.Student;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "mentor")

public class Mentor {
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

    @OneToMany(mappedBy = "mentor")
    private List<Slot> slots = new ArrayList<>();

    @ManyToMany(mappedBy = "mentors")
    @JsonIgnoreProperties("mentors")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();

    public MentorDTO toMentorDTO(){
       return  new MentorDTO(this.getFirstname(), this.getLastname(), this.getTitle(), this.getDescription(), this.getImgUrl(), this.getGithubUrl(), this.getLinkedinUrl(), this.getUser().getId());
    }
}
