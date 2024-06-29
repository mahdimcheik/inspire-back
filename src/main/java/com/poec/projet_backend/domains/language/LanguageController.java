package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.domains.formation.FormationService;
import lombok.Data;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@RestController
@RequestMapping("/language")
public class LanguageController {

    private final LanguageService service;

    @GetMapping("/get/all")
    public List<LanguageDTO> getAllLanguages() {
        return service.getAllLanguages();
    }

    @PostMapping("/add")
    public Language addLanguage(@RequestBody Language language) {
        try {
            return service.addLanguage(language);
        }
        catch (Exception e) {
            return null;
        }

    }
}

