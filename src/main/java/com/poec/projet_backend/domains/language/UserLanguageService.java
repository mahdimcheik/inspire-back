package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class UserLanguageService {
    private final UserAppRepository userRepository;
    private final LanguageRepository languageRepository;


    public List<LanguageDTO> getLanguagesByUserId(Long id) {
        try {
            UserApp user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            return user.getLanguages().stream().map(LanguageDTO::fromLanguage).toList();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseLanguage updateUserLanguageList(Long id, List<Language> languages) {
        try {
            UserApp user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            if (user != null) {
                user.setLanguages(languages);
                var response = ResponseLanguage.builder().success(true)
                        .languages(userRepository.save(user).getLanguages().stream().map(LanguageDTO::fromLanguage).toList())
                        .message("Langauge updated successfully")
                        .build();
                return response;
            }
            var response = ResponseLanguage.builder().success(false)
                    .message("Echec")
                    .build();
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
