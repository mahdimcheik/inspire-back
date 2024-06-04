package com.poec.projet_backend.user_app;

import com.poec.projet_backend.domains.language.Language;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/language/user")
public class UserLanguageController {

    private final UserLanguageService userLanguageService;

    @PutMapping("/update/{userId}")
    public List<Language> update(@PathVariable Long userId, @RequestBody List<Language> languages) {
        return userLanguageService.updateUseranguageList(userId, languages);
    }
}
