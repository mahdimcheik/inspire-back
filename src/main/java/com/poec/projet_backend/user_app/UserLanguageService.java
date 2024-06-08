package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageDTO;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.domains.language.ResponseLanguage;
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

    public ResponseLanguage updateUserLanguageList(Long id, List<Language> languages) {
        UserApp user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("User not found"));
        if(user != null){
            user.setLanguages(languages);
            var response = ResponseLanguage.builder().success(true)
                    .languages(userRepository.save(user).getLanguages().stream().map(LanguageDTO::fromLanguage).toList())
                    .message("All languages")
                    .build();
            return response;
        }


        var response = ResponseLanguage.builder().success(false)
                .message("nothing ")
                .build();
        return response;
    }
}
