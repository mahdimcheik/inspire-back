package com.poec.projet_backend.user_app;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/langue/user")
public class UserLanguageController {

    private final UserLanguageService userLanguageService;
}
