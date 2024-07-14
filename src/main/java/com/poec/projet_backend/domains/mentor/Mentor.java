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

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "githubUrl")
    private String githubUrl;

    @Column(name = "linkedinUrl")
    private String linkedinUrl;

    @OneToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties("mentor")
    private UserApp user;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("mentor")
    private List<Slot> slots = new ArrayList<>();

    @ManyToMany(mappedBy = "mentors")
    @JsonIgnoreProperties("mentors")
    @JsonIgnore
    private List<Student> students = new ArrayList<>();
}
