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


    public List<LanguageDTO> getLanguagesByUserId(Long id) {
        UserApp user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        return user.getLanguages().stream().map(LanguageDTO::fromLanguage).toList();
    }

    public List<LanguageDTO> updateUserLanguageList(Long id, List<Language> languages) {
        UserApp user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));

        user.setLanguages(languages);
        return userRepository.save(user).getLanguages().stream().map(LanguageDTO::fromLanguage).toList();
    }
}
