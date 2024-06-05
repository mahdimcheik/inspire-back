package com.poec.projet_backend.util;

import com.poec.projet_backend.domains.language.Language;
import com.poec.projet_backend.domains.language.LanguageRepository;
import com.poec.projet_backend.user_app.Role;
import com.poec.projet_backend.user_app.UserApp;
import com.poec.projet_backend.user_app.UserAppRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserAppRepository userAppRepository;
    private final PasswordEncoder passwordEncoder;
    private final LanguageRepository languageRepository;

    @Override
    public void run(String... args) throws Exception {
        if(this.userAppRepository.findByEmail("admin@admin.com").isEmpty()) {
            this.createAdmin();
            this.createUsers();
        }
        createLanguage("francais");
        createLanguage("deutsch");
        createLanguage("arabe");
        createLanguage("chinois");
        createLanguage("japonais");

    }

    private void createAdmin() {
        UserApp admin = UserApp.builder()
                .email("admin@admin.com")
                .password(passwordEncoder.encode("admin"))
                .role("ROLE_" + Role.ADMIN)
                .build();

        this.userAppRepository.save(admin);
    }

    private void createUsers() {
        UserApp user1 = UserApp.builder()
                .email("user1@user1.com")
                .password(passwordEncoder.encode("user1"))
                .role("ROLE_" + Role.USER)
                .build();

        this.userAppRepository.save(user1);
    }

    private void createLanguage(String newName){
        Language language = new Language();
        language.setName(newName);
        languageRepository.save(language);
    }
}
