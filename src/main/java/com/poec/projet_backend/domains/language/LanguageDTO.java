package com.poec.projet_backend.domains.language;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LanguageDTO {
    private String name;
    private Long userId;

}
