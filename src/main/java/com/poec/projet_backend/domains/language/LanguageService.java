package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.domains.formation.FormationRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Data
@Service
public class LanguageService {

    private LanguageRepository repository;
}
