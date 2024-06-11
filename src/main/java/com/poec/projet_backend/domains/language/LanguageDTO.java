package com.poec.projet_backend.domains.language;

import lombok.Builder;
import lombok.Data;


@Builder
public record  LanguageDTO (
    String name,
    Long id)
    {
        public static LanguageDTO fromLanguage(Language language){
            return new LanguageDTO(language.getName(), language.getId());
        }

}
