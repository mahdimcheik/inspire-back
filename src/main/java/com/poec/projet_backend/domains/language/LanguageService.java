package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.domains.formation.FormationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class LanguageService {

    private final LanguageRepository repository;

    public List<LanguageDTO> getAllLanguages() {
        return repository.findAll().stream().map(LanguageDTO::fromLanguage).toList();
    }

    public Language addLanguage(Language language) {
        System.out.println(language.toString());
        return repository.save(language);
    }

    }
