package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageDTO;
import com.poec.projet_backend.domains.language.LanguageRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserLanguageService {
    private final UserAppRepository userRepository;
    private final LanguageRepository languageRepository;



}
