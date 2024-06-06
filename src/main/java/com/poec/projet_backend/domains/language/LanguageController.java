package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.domains.formation.FormationService;
import lombok.Data;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
