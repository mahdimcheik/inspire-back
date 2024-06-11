package com.poec.projet_backend.domains.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    @ManyToMany( mappedBy = "skills")
    @JsonIgnoreProperties("skills")
    @JsonIgnore
    private List<UserApp> users = new ArrayList<>();
    public Skill(Long id,String name) {
        this.id = id;
        this.name = name;
    }
}