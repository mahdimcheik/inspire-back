package com.poec.projet_backend.domains.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.domains.experience.Experience;
import com.poec.projet_backend.domains.mentor.Mentor;
import com.poec.projet_backend.domains.reservation.Reservation;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
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

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("student")
    private List<Reservation> reservation = new ArrayList<>();
}