package com.poec.projet_backend.domains.language;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.poec.projet_backend.user_app.UserApp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToMany( mappedBy = "languages")
    @JsonIgnoreProperties("languages")
    @JsonIgnore
    private List<UserApp> users = new ArrayList<>();


}
