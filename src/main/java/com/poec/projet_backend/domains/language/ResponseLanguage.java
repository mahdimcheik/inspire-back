package com.poec.projet_backend.domains.language;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseLanguage {
    private String message;
    private boolean success;
    private List<LanguageDTO> languages;
}
