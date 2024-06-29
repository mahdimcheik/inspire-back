package com.poec.projet_backend.domains.language;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/language/user")
public class UserLanguageController {

    private final UserLanguageService userLanguageService;

    @PutMapping("/update/{userId}")
    public ResponseLanguage update(@PathVariable Long userId, @RequestBody List<Language> languages) {
        return userLanguageService.updateUserLanguageList(userId, languages);
    }
    @GetMapping("/{userId}")
    public List<LanguageDTO> getLanguageByUserId(@PathVariable Long userId) {
        return userLanguageService.getLanguagesByUserId(userId);
    }
}
