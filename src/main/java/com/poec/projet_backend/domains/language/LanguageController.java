package com.poec.projet_backend.domains.language;

import com.poec.projet_backend.domains.formation.FormationService;
import lombok.Data;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/api/v1/language")
public class LanguageController {

    private LanguageService service;
}
