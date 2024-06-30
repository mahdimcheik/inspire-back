package com.poec.projet_backend.domains.language;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Data
@RequestMapping("/language/user")
public class UserLanguageController {

    private final UserLanguageService userLanguageService;

    @PutMapping("/update/{userId}")
    public ResponseEntity<ResponseLanguage> update(@PathVariable Long userId, @RequestBody List<Language> languages) {
        try {
            return new ResponseEntity<>(userLanguageService.updateUserLanguageList(userId, languages), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LanguageDTO>> getLanguageByUserId(@PathVariable Long userId) {
        try {
            return new ResponseEntity<>(userLanguageService.getLanguagesByUserId(userId), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
